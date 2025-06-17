package com.sample.factpedia.features.categories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.features.categories.domain.usecase.GetFactsByCategoryIdUseCase
import com.sample.factpedia.features.categories.presentation.actions.FactsByCategoryScreenAction
import com.sample.factpedia.features.categories.presentation.state.FactsByCategoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactsByCategoryViewModel @Inject constructor(
    private val getFactsByCategoryIdUseCase: GetFactsByCategoryIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(FactsByCategoryScreenState())
    val state = _state.asStateFlow()

    fun onAction(action: FactsByCategoryScreenAction) {
        when(action) {
            is FactsByCategoryScreenAction.LoadFactsByCategory -> loadFactsByCategory(action.categoryId)
        }
    }

    private fun loadFactsByCategory(categoryId: Int) {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            getFactsByCategoryIdUseCase(categoryId).fold(
                onSuccess = { facts ->
                    _state.update { currentState ->
                        currentState.copy(
                            facts = facts,
                            isLoading = false,
                        )
                    }
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
}