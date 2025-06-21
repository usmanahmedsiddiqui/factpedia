package com.sample.factpedia.features.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.core.domain.usecase.ToggleBookmarkUseCase
import com.sample.factpedia.features.search.domain.usecase.SearchFactsUseCase
import com.sample.factpedia.features.search.presentation.action.SearchScreenAction
import com.sample.factpedia.features.search.presentation.state.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchFactsUseCase: SearchFactsUseCase,
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest {
                    if (it.isBlank()) {
                        flowOf(Response.Success(emptyList()))
                    } else {
                        searchFactsUseCase(it)
                    }
                }
                .collect { response ->
                    response.fold(
                        { facts ->
                            _state.update {
                                it.copy(searchResults = facts, error = null)
                            }
                        },
                        { error ->
                            _state.update {
                                it.copy(searchResults = emptyList(), error = error)
                            }
                        }
                    )
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

    private fun toggleBookmark(factId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            toggleBookmarkUseCase(factId, isBookmarked)
        }
    }
}