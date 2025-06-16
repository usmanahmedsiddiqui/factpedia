package com.sample.factpedia.features.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.features.search.domain.usecase.SearchFactsUseCase
import com.sample.factpedia.features.search.presentation.state.SearchScreenAction
import com.sample.factpedia.features.search.presentation.state.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchFactsUseCase: SearchFactsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state
        .onStart {
            observeSearchQuery()
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            SearchScreenState()
        )

    private val searchQuery = MutableSharedFlow<String>(replay = 1)

    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .onEach { query ->
                    performSearch(query)
                }
                .launchIn(this)
        }
    }

    fun onAction(searchScreenAction: SearchScreenAction) {
        when (searchScreenAction) {
            is SearchScreenAction.TriggerSearch -> {
                _state.update { it.copy(query = searchScreenAction.query, error = null) }
                viewModelScope.launch {
                    searchQuery.emit(searchScreenAction.query)
                }
            }

            is SearchScreenAction.ClearSearch -> {
                _state.update {
                    it.copy(
                        query = "",
                        searchResults = emptyList(),
                        error = null
                    )
                }
            }

        }
    }

    private suspend fun performSearch(query: String) {
        searchFactsUseCase(query).fold(
            onSuccess = { facts ->
                _state.update { it.copy(searchResults = facts, error = null) }
            },
            onFailure = { error ->
                _state.update { it.copy(error = error, searchResults = emptyList()) }
            }
        )
    }
}