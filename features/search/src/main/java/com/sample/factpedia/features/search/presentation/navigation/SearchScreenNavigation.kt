package com.sample.factpedia.features.search.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sample.factpedia.features.search.presentation.ui.SearchRoute
import kotlinx.serialization.Serializable

@Serializable
object SearchScreenRoute

fun NavHostController.navigateToSearch() {
    this.navigate(SearchScreenRoute)
}

fun NavGraphBuilder.searchScreen() {
    composable<SearchScreenRoute> { SearchRoute() }
}
