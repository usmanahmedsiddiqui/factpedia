package com.sample.factpedia.features.categories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.features.categories.domain.usecase.GetCategoriesUseCase
import com.sample.factpedia.features.categories.presentation.state.CategoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryScreenState())
    val state = _state
        .onStart {
            loadCategories()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            CategoryScreenState()
        )

    private fun loadCategories() {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            getCategoriesUseCase().fold(
                onSuccess = { categories ->
                    _state.update { currentState ->
                        currentState.copy(
                            categories = categories,
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