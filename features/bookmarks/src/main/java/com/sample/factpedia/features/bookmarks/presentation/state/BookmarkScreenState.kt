package com.sample.factpedia.features.bookmarks.presentation.state

import com.sample.factpedia.core.model.domain.BookmarkedFact

data class BookmarkScreenState(
    val facts: List<BookmarkedFact> = emptyList(),
)