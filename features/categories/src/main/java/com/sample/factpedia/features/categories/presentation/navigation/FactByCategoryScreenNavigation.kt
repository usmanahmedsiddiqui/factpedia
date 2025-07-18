package com.sample.factpedia.features.categories.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sample.factpedia.features.categories.presentation.ui.FactsByCategoryRoute
import kotlinx.serialization.Serializable

@Serializable
data class FactsByCategoryScreenRoute(val categoryId: Int, val categoryName: String)

fun NavHostController.navigateToFactByCategoryScreen(categoryId: Int, categoryName: String) {
    this.navigate(FactsByCategoryScreenRoute(categoryId, categoryName))
}

fun NavGraphBuilder.factByCategoryScreen() {
    composable<FactsByCategoryScreenRoute> {
        val args = it.toRoute<FactsByCategoryScreenRoute>()
        FactsByCategoryRoute(args.categoryId, args.categoryName)
    }
}