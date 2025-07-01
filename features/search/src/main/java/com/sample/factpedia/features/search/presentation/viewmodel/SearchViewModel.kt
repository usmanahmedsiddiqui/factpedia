package com.sample.factpedia.features.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.core.data.repository.FactsRepository
import com.sample.factpedia.core.domain.ToggleBookmarkUseCase
import com.sample.factpedia.features.search.data.repository.SearchRepository
import com.sample.factpedia.features.search.domain.usecase.SearchFactsFromLocalDatabaseUseCase
import com.sample.factpedia.features.search.presentation.action.SearchScreenAction
import com.sample.factpedia.features.search.presentation.state.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val factsRepository: FactsRepository,
    private val searchFactsFromLocalDatabaseUseCase: SearchFactsFromLocalDatabaseUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
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
    private val isSyncing = MutableStateFlow(true)


    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .onEach { query ->
                    if(query.isBlank()) {
                        _state.update { it.copy(searchResults = emptyList(), error = null) }
                    }
                }
                .filter { it.isNotBlank() }
                .collect { query ->
                    performRemoteSearch(query)
                }
        }

        viewModelScope.launch {
            viewModelScope.launch {
                isSyncing.filter { isSyncInProcess ->
                    !isSyncInProcess
                }.collect {
                    performLocalSearch()
                }
            }
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

            is SearchScreenAction.ToggleBookmark -> toggleBookmark(
                searchScreenAction.factId,
                searchScreenAction.isBookmarked
            )

            is SearchScreenAction.ClearSearch -> {
                _state.update { it.copy(query = "") }
                viewModelScope.launch {
                    searchQuery.emit("")
                }
            }
        }
    }

    private fun performRemoteSearch(query: String) {
        isSyncing.update { true }
        viewModelScope.launch {
            searchRepository.search(query)
                .fold(
                    onSuccess = { result ->
                        factsRepository.upsertFacts(result)
                        isSyncing.update { false }
                    },
                    onFailure = { error ->
                        _state.update { it.copy(error = error) }
                        isSyncing.update { false }
                    }
                )
        }
    }

    private fun performLocalSearch() {
        viewModelScope.launch {
            val query = state.value.query
            searchFactsFromLocalDatabaseUseCase(query)
                .collect {
                    _state.update { currentState ->
                        currentState.copy(
                            searchResults = it,
                        )
                    }
                }
        }
    }

    private fun toggleBookmark(factId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            toggleBookmarkUseCase(factId, isBookmarked)
        }
    }
}