package com.sample.factpedia.features.categories.presentation.state

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.model.domain.BookmarkedFact

data class FactsByCategoryScreenState (
    val facts: List<BookmarkedFact> = emptyList(),
    val isLoading: Boolean = false,
    val error: DataError? = null
)