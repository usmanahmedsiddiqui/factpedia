package com.sample.factpedia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sample.factpedia.features.bookmarks.presentation.navigation.bookmarksScreen
import com.sample.factpedia.features.categories.presentation.navigation.categoryListScreen
import com.sample.factpedia.features.categories.presentation.navigation.factByCategoryScreen
import com.sample.factpedia.features.categories.presentation.navigation.navigateToFactByCategoryScreen
import com.sample.factpedia.features.feed.presentation.navigation.FeedScreenRoute
import com.sample.factpedia.features.feed.presentation.navigation.feedScreen
import com.sample.factpedia.features.search.presentation.navigation.searchScreen
import com.sample.factpedia.features.settings.presentation.navigation.settingsScreen

@Composable
fun FactPediaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = FeedScreenRoute,
        modifier = modifier
    ) {
        feedScreen()
        bookmarksScreen()

        categoryListScreen { category ->
            navController.navigateToFactByCategoryScreen(category.id, category.name)
        }

        factByCategoryScreen()

        searchScreen()

        settingsScreen()
    }
}