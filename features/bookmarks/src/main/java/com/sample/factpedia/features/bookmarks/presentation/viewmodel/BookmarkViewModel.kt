package com.sample.factpedia.features.bookmarks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.factpedia.core.domain.usecase.ToggleBookmarkUseCase
import com.sample.factpedia.features.bookmarks.domain.usecase.GetBookmarkedFactsUseCase
import com.sample.factpedia.features.bookmarks.presentation.action.BookmarkScreenAction
import com.sample.factpedia.features.bookmarks.presentation.state.BookmarkScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkedFactsUseCase: GetBookmarkedFactsUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(BookmarkScreenState())
    val state = _state
        .onStart {
            observeBookmarks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            BookmarkScreenState()
        )

    fun onAction(action: BookmarkScreenAction) {
        when(action) {
            is BookmarkScreenAction.ToggleBookmark -> toggleBookmark(action.factId, action.isBookmarked)
        }
    }

    private fun toggleBookmark(factId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            toggleBookmarkUseCase(factId, isBookmarked)
        }
    }

    private fun observeBookmarks() {
        viewModelScope.launch {
            getBookmarkedFactsUseCase()
                .collect { facts ->
                    _state.update { currentState ->
                        currentState.copy(
                            facts = facts
                        )
                    }
                }
        }
    }
}