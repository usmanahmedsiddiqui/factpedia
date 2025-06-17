package com.sample.factpedia.features.categories.presentation.actions

sealed class FactsByCategoryScreenAction {
    data class LoadFactsByCategory(val categoryId: Int) : FactsByCategoryScreenAction()
}