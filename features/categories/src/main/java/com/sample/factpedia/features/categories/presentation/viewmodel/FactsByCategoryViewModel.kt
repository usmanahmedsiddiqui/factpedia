package com.sample.factpedia.features.categories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.features.categories.domain.usecase.GetFactsByCategoryIdUseCase
import com.sample.factpedia.features.categories.presentation.state.FactsByCategoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactsByCategoryViewModel @Inject constructor(
    private val getFactsByCategoryIdUseCase: GetFactsByCategoryIdUseCase
): ViewModel() {
    private val _state = MutableStateFlow(FactsByCategoryScreenState())
    val state = _state
        .onStart {
            loadFactsByCategory()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            FactsByCategoryScreenState()
        )

    private fun loadFactsByCategory() {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            getFactsByCategoryIdUseCase(1).fold(
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