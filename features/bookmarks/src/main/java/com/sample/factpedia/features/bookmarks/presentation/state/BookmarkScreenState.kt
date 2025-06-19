package com.sample.factpedia.features.bookmarks.presentation.state

import com.sample.factpedia.core.domain.model.Fact

data class BookmarkScreenState(
    val facts: List<Fact> = emptyList(),
)