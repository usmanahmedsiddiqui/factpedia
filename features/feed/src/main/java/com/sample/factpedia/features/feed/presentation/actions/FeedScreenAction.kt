package com.sample.factpedia.features.feed.presentation.actions

import com.sample.factpedia.core.model.domain.BookmarkedFact

sealed class FeedScreenAction {
    data object Retry : FeedScreenAction()
    data class ShareFact(val fact: BookmarkedFact) : FeedScreenAction()
    data object Refresh : FeedScreenAction()
    data class ToggleBookmark(val factId: Int, val isBookmarked: Boolean) : FeedScreenAction()
}