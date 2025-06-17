package com.sample.factpedia.features.categories.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data class FactsByCategoryScreenRoute(val categoryId: Int, val categoryNane: String)