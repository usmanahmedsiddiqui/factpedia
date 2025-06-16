package com.sample.factpedia.features.categories.presentation.state

sealed class FactsByCategoryScreenAction {
    data class LoadFactsByCategory(val categoryId: Int) : FactsByCategoryScreenAction()
}