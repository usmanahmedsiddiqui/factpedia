package com.sample.factpedia.features.categories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.features.categories.domain.usecase.GetFactsByCategoryIdUseCase
import com.sample.factpedia.features.categories.domain.usecase.LoadRemoteFactsByCategoryUseCase
import com.sample.factpedia.features.categories.domain.usecase.SyncFactsByCategoriesUseCase
import com.sample.factpedia.features.categories.presentation.actions.FactsByCategoryScreenAction
import com.sample.factpedia.features.categories.presentation.state.FactsByCategoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactsByCategoryViewModel @Inject constructor(
    private val getFactsByCategoryIdUseCase: GetFactsByCategoryIdUseCase,
    private val loadRemoteFactsByCategoryUseCase: LoadRemoteFactsByCategoryUseCase,
    private val syncFactsByCategoriesUseCase: SyncFactsByCategoriesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(FactsByCategoryScreenState())
    val state = _state.asStateFlow()

    fun onAction(action: FactsByCategoryScreenAction) {
        when(action) {
            is FactsByCategoryScreenAction.LoadFactsByCategory -> observeFacts(action.categoryId)
            is FactsByCategoryScreenAction.RetryClicked -> retry(action.categoryId)
        }
    }

    private fun observeFacts(categoryId: Int) {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            getFactsByCategoryIdUseCase(categoryId)
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
            loadRemoteFactsByCategoryUseCase(categoryId).fold(
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