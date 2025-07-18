package com.sample.factpedia.features.categories.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sample.factpedia.features.categories.domain.model.Category
import com.sample.factpedia.features.categories.presentation.ui.CategoryListRoute
import kotlinx.serialization.Serializable

@Serializable
object CategoryListScreenRoute

fun NavHostController.navigateToFactByCategoryScreen(navOptions: NavOptions) {
    this.navigate(CategoryListScreenRoute, navOptions)
}

fun NavGraphBuilder.categoryListScreen(onCategoryClick: (Category) -> Unit) {
    composable<CategoryListScreenRoute> {
        CategoryListRoute(
            onCategoryClick = onCategoryClick
        )
    }
}