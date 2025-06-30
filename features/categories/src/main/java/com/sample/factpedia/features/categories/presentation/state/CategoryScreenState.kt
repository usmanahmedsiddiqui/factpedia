package com.sample.factpedia.features.categories.presentation.state

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.features.categories.domain.model.Category

data class CategoryScreenState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: DataError? = null,
)