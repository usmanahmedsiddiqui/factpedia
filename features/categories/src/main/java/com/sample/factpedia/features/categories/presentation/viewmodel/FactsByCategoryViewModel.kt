package com.sample.factpedia.features.categories.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.core.domain.ToggleBookmarkUseCase
import com.sample.factpedia.features.categories.data.repository.FactsByCategoryRepository
import com.sample.factpedia.features.categories.domain.usecase.GetFactsByCategoryUseCase
import com.sample.factpedia.features.categories.domain.usecase.SyncFactsByCategoriesUseCase
import com.sample.factpedia.features.categories.presentation.actions.FactsByCategoryScreenAction
import com.sample.factpedia.features.categories.presentation.state.FactsByCategoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactsByCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val factsByCategoryRepository: FactsByCategoryRepository,
    private val getFactsByCategoryUseCase: GetFactsByCategoryUseCase,
    private val syncFactsByCategoriesUseCase: SyncFactsByCategoriesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
) : ViewModel() {

    private val categoryId: Int = checkNotNull(savedStateHandle["categoryId"])

    private val _state = MutableStateFlow(FactsByCategoryScreenState())
    val state = _state
        .onStart { sync() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            FactsByCategoryScreenState()
        )

    private val isSyncing = MutableStateFlow(true)

    private fun sync() {
        viewModelScope.launch {
            isSyncing.filter { isSyncInProcess ->
                !isSyncInProcess
            }.collect {
                fetchLocalFacts()
            }
        }

        fetchRemoteFacts(categoryId)
    }

    fun onAction(action: FactsByCategoryScreenAction) {
        when (action) {
            is FactsByCategoryScreenAction.RetryClicked -> fetchRemoteFacts(action.categoryId)
            is FactsByCategoryScreenAction.ToggleBookmark -> toggleBookmark(
                action.factId,
                action.isBookmarked
            )
        }
    }

    private fun toggleBookmark(factId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            toggleBookmarkUseCase(factId, isBookmarked)
        }
    }

    private fun fetchRemoteFacts(categoryId: Int) {
        isSyncing.update { true }

        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
                error = null,
                facts = emptyList(),
            )
        }

        viewModelScope.launch {
            factsByCategoryRepository.loadRemoteFactsByCategoryId(categoryId).fold(
                onSuccess = { facts ->
                    syncFactsByCategoriesUseCase(categoryId = categoryId, facts = facts)
                    isSyncing.update { false }
                },
                onFailure = { error ->
                    _state.update { currentState ->
                        currentState.copy(error = error)
                    }
                    isSyncing.update { false }
                }
            )
        }
    }

    private fun fetchLocalFacts() {
        viewModelScope.launch {
            getFactsByCategoryUseCase(categoryId)
                .collect { facts ->
                    _state.update { currentState ->
                        currentState.copy(
                            facts = facts,
                            isLoading = false,
                        )
                    }
                }
        }
    }
}