package com.sample.factpedia.features.categories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.domain.usecase.SyncCategoriesUseCase
import com.sample.factpedia.features.categories.presentation.actions.CategoryScreenAction
import com.sample.factpedia.features.categories.presentation.state.CategoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val syncCategoriesUseCase: SyncCategoriesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryScreenState())
    val state = _state
        .onStart {
            isSyncing.update { true }
            observeCategories()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            CategoryScreenState()
        )

    private val isSyncing = MutableStateFlow(false)

    fun onAction(action: CategoryScreenAction) {
        when (action) {
            CategoryScreenAction.RetryClicked -> {
                retry()
            }
        }
    }

    private fun retry() {
        fetchRemoteCategories()
    }

    private fun observeCategories() {
        viewModelScope.launch {
            categoryRepository.getCategoriesFromLocalDatabase()
                .collect { categories ->
                    _state.update { currentState ->
                        currentState.copy(
                            categories = categories,
                            isLoading = isSyncing.value
                        )
                    }
                }
        }

        fetchRemoteCategories()
    }

    private fun fetchRemoteCategories() {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            categoryRepository.loadRemoteCategories().fold(
                onSuccess = { categories ->
                    syncCategoriesUseCase(categories)
                    isSyncing.update { false }
                },
                onFailure = { error ->
                    _state.update { currentState ->
                        currentState.copy(
                            error = error,
                            isLoading = false,
                        )
                    }

                    isSyncing.update { false }
                }
            )
        }
    }
}