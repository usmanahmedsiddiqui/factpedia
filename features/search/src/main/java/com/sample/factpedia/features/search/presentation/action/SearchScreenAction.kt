package com.sample.factpedia.features.search.presentation.action

sealed class SearchScreenAction {
    data class TriggerSearch(val query: String) : SearchScreenAction()
    data object ClearSearch : SearchScreenAction()
}