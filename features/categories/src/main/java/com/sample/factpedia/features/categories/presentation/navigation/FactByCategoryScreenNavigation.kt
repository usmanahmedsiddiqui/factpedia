package com.sample.factpedia.features.categories.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sample.factpedia.features.categories.presentation.ui.FactsByCategoryScreen
import kotlinx.serialization.Serializable

@Serializable
data class FactsByCategoryScreenRoute(val categoryId: Int, val categoryName: String)

fun NavHostController.navigateToFactByCategoryScreen(categoryId: Int, categoryName: String) {
    this.navigate(FactsByCategoryScreenRoute(categoryId, categoryName))
}

fun NavGraphBuilder.factByCategoryScreen() {
    composable<FactsByCategoryScreenRoute> {
        val args = it.toRoute<FactsByCategoryScreenRoute>()
        FactsByCategoryScreen(args.categoryId, args.categoryName)
    }
}