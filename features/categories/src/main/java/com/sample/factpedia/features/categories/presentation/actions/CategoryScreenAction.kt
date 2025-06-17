package com.sample.factpedia.features.categories.presentation.actions

sealed interface CategoryScreenAction {
    data object RetryClicked: CategoryScreenAction
}