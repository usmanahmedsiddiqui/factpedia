package com.sample.factpedia.features.bookmarks.presentation.action

sealed class BookmarkScreenAction {
    data class ToggleBookmark(val factId: Int, val isBookmarked: Boolean) : BookmarkScreenAction()
}