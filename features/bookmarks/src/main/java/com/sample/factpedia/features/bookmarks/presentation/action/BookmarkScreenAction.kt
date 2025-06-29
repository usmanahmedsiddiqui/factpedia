package com.sample.factpedia.features.bookmarks.presentation.action

sealed class BookmarkScreenAction {
    data class RemoveBookmark(val factId: Int) : BookmarkScreenAction()
}