package com.sample.factpedia.features.categories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.features.categories.domain.usecase.GetCategoriesUseCase
import com.sample.factpedia.features.categories.domain.usecase.LoadRemoteCategoriesUseCase
import com.sample.factpedia.features.categories.domain.usecase.SyncCategoriesUseCase
import com.sample.factpedia.features.categories.presentation.actions.CategoryScreenAction
import com.sample.factpedia.features.categories.presentation.state.CategoryScreenState
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
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val loadRemoteCategoriesUseCase: LoadRemoteCategoriesUseCase,
    private val syncCategoriesUseCase: SyncCategoriesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryScreenState())
    val state = _state
        .onStart {
            observeCategories()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            CategoryScreenState()
        )

    fun onAction(action: CategoryScreenAction) {
        when (action) {
            CategoryScreenAction.RetryClicked -> {
                retry()
            }
        }
    }

    private fun retry() {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
                error = null
            )
        }
        syncCategories()
    }

    private fun observeCategories() {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            getCategoriesUseCase()
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .collect { categories ->
                    /**
                     * Update categories from local database
                     */
                    _state.update { currentState ->
                        currentState.copy(
                            categories = categories,
                            isLoading = false,
                        )
                    }
                }
        }

        syncCategories()
    }

    private fun syncCategories() {
        viewModelScope.launch {
            loadRemoteCategoriesUseCase().fold(
                onSuccess = { categories ->
                    /**
                     * When receive successful response from server sync it with
                     * local database
                     */
                    syncCategoriesUseCase(categories)
                },
                onFailure = { error ->
                    /**
                     * It is possible that the local db has the data and
                     * server returns error so update the state accordingly
                     */
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
}