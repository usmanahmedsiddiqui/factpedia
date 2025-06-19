package com.sample.factpedia.features.feed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.common.result.fold
import com.sample.factpedia.core.domain.usecase.ToggleBookmarkUseCase
import com.sample.factpedia.database.dao.BookmarkDao
import com.sample.factpedia.features.feed.domain.usecase.GetRandomFactUse
import com.sample.factpedia.features.feed.domain.usecase.LoadRemoteFactsUseCase
import com.sample.factpedia.features.feed.domain.usecase.SyncFactsUseCase
import com.sample.factpedia.features.feed.presentation.actions.FeedScreenAction
import com.sample.factpedia.features.feed.presentation.state.FeedScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(
    private val getRandomFactUse: GetRandomFactUse,
    private val loadRemoteFactsUseCase: LoadRemoteFactsUseCase,
    private val syncFactsUseCase: SyncFactsUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val bookmarkDao: BookmarkDao,
) : ViewModel() {
    private val _state = MutableStateFlow(FeedScreenState())
    val state = _state
        .onStart {
            observeBookmarks()
            loadFact()
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            FeedScreenState()
        )

    fun onAction(action: FeedScreenAction) {
        when (action) {
            FeedScreenAction.Retry -> loadFact()
            is FeedScreenAction.ToggleBookmark -> toggleBookmark(action.factId, action.isBookmarked)
            FeedScreenAction.Refresh -> refreshFact()
            is FeedScreenAction.ShareFact -> {}
        }
    }

    private fun refreshFact() {
        _state.update { it.copy(refreshCount = it.refreshCount + 1) }
        if (state.value.refreshCount % 3 == 0) {
            prefetchFactsInBackground()
        }
        loadFact()
    }

    private fun prefetchFactsInBackground() {
        viewModelScope.launch {
            loadRemoteFactsUseCase(LIMIT).fold(
                onSuccess = { syncFactsUseCase(it) },
                onFailure = {} // silent fail is fine for background load
            )
        }
    }

    private fun loadFact() {
        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val fact = getRandomFactUse(state.value.fact?.id)
            if (fact != null) {
                _state.update {
                    it.copy(
                        fact = fact,
                        isLoading = false
                    )
                }
            } else {
                fetchRemoteFacts()
            }
        }
    }

    private fun fetchRemoteFacts() {
        viewModelScope.launch {
            loadRemoteFactsUseCase(LIMIT).fold(
                onSuccess = { facts ->
                    syncFactsUseCase(facts)
                    loadFact()
                },
                onFailure = { error ->
                    _state.update { it.copy(error = error, isLoading = false) }
                }
            )
        }
    }

    private fun toggleBookmark(factId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            toggleBookmarkUseCase(factId, isBookmarked)
        }
    }

    private fun observeBookmarks() {
        viewModelScope.launch {
            bookmarkDao.getAllBookmarks().collectLatest { bookmarks ->
                val currentFact = state.value.fact ?: return@collectLatest
                val isBookmarked = bookmarks.any { it.factId == currentFact.id }

                _state.update {
                    it.copy(fact = currentFact.copy(isBookmarked = isBookmarked))
                }
            }
        }
    }
}

private const val LIMIT = 10

