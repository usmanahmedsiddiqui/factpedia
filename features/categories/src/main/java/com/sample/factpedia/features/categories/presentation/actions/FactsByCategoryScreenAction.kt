package com.sample.factpedia.features.categories.presentation.actions

sealed class FactsByCategoryScreenAction {
    data class RetryClicked(val categoryId: Int) : FactsByCategoryScreenAction()
    data class ToggleBookmark(val factId: Int, val isBookmarked: Boolean) : FactsByCategoryScreenAction()
}