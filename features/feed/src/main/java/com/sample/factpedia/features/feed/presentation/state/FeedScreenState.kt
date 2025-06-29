package com.sample.factpedia.features.feed.presentation.state

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.model.domain.BookmarkedFact

data class FeedScreenState(
    val isLoading: Boolean = false,
    val fact: BookmarkedFact? = null,
    val error: DataError? = null,
    val refreshCount: Int = 0
)