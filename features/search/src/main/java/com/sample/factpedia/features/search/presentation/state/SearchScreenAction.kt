package com.sample.factpedia.features.search.presentation.state

sealed class SearchScreenAction {
    data class TriggerSearch(val query: String) : SearchScreenAction()
    data object ClearSearch : SearchScreenAction()
}