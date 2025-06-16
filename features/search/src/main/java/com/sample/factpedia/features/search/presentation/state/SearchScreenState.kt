package com.sample.factpedia.features.search.presentation.state

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.domain.model.Fact

data class SearchScreenState(
    val query: String = "",
    val error: DataError? = null,
    val searchResults: List<Fact> = emptyList()
)