package com.sample.factpedia.features.categories.presentation.state

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.model.domain.Fact

data class FactsByCategoryScreenState (
    val facts: List<Fact> = emptyList(),
    val isLoading: Boolean = false,
    val error: DataError? = null
)