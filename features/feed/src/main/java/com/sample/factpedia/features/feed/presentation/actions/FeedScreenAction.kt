package com.sample.factpedia.features.feed.presentation.actions

import com.sample.factpedia.core.domain.model.Fact

sealed class FeedScreenAction {
    data object Retry : FeedScreenAction()
    data class ShareFact(val fact: Fact) : FeedScreenAction()
    data object Refresh : FeedScreenAction()
    data class ToggleBookmark(val factId: Int, val isBookmarked: Boolean) : FeedScreenAction()
}