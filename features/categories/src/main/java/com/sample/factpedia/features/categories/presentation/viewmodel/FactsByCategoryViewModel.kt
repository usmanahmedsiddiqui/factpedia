package com.sample.factpedia.features.categories.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.core.domain.ToggleBookmarkUseCase
import com.sample.factpedia.features.categories.data.repository.FactsByCategoryRepository
import com.sample.factpedia.features.categories.domain.usecase.SyncFactsByCategoriesUseCase
import com.sample.factpedia.features.categories.presentation.actions.FactsByCategoryScreenAction
import com.sample.factpedia.features.categories.presentation.state.FactsByCategoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
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
    private val syncFactsByCategoriesUseCase: SyncFactsByCategoriesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
) : ViewModel() {

    private val categoryId: Int = checkNotNull(savedStateHandle["categoryId"])

    private val _state = MutableStateFlow(FactsByCategoryScreenState())

    val state = _state
        .onStart {
            observeFacts(categoryId)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            FactsByCategoryScreenState()
        )

    fun onAction(action: FactsByCategoryScreenAction) {
        when(action) {
            is FactsByCategoryScreenAction.RetryClicked -> retry(action.categoryId)
            is FactsByCategoryScreenAction.ToggleBookmark -> toggleBookmark(action.factId, action.isBookmarked)
        }
    }

    private fun toggleBookmark(factId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            toggleBookmarkUseCase(factId, isBookmarked)
        }
    }

    private fun observeFacts(categoryId: Int) {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            factsByCategoryRepository.getFactsByCategoryIdFromLocalDatabase(categoryId)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .collect { facts ->
                    /**
                     * Update facts from local database
                     */
                    _state.update { currentState ->
                        currentState.copy(
                            facts = facts,
                            isLoading = false,
                        )
                    }
                }
        }

        syncFacts(categoryId)
    }

    private fun syncFacts(categoryId: Int) {
        viewModelScope.launch {
            factsByCategoryRepository.loadRemoteFactsByCategoryId(categoryId).fold(
                onSuccess = { facts ->
                    /**
                     * When receive successful response from server sync it with
                     * local database
                     */
                    syncFactsByCategoriesUseCase(categoryId = categoryId, facts = facts)
                },
                onFailure = { error ->
                    _state.update { currentState ->
                        currentState.copy(
                            error = error,
                            isLoading = false,
                        )
                    }
                }
            )
        }
    }

    private fun retry(categoryId: Int) {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
                error = null
            )
        }

        syncFacts(categoryId)
    }
}