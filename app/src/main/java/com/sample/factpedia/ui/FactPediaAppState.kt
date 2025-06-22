package com.sample.factpedia.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sample.factpedia.features.bookmarks.presentation.navigation.navigateToBookmark
import com.sample.factpedia.features.categories.presentation.navigation.FactsByCategoryScreenRoute
import com.sample.factpedia.features.categories.presentation.navigation.navigateToFactByCategoryScreen
import com.sample.factpedia.features.feed.presentation.navigation.navigateToFeed
import com.sample.factpedia.features.search.presentation.navigation.SearchScreenRoute
import com.sample.factpedia.features.search.presentation.navigation.navigateToSearch
import com.sample.factpedia.features.settings.presentation.navigation.SettingsScreenRoute
import com.sample.factpedia.features.settings.presentation.navigation.navigateToSettings
import com.sample.factpedia.navigation.TopLevelDestination

@Composable
fun rememberFactPediaAppState(
    navController: NavHostController = rememberNavController(),
): FactPediaAppState {
    return remember(navController) {
        FactPediaAppState(
            navController = navController
        )
    }
}

@Stable
class FactPediaAppState(
    val navController: NavHostController,
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val currentDestination: NavDestination?
        @Composable get() {
            // Collect the currentBackStackEntryFlow as a state
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            // Fallback to previousDestination if currentEntry is null
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return topLevelDestinations.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(topLevelDestination.route) == true
            }
        }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.FEED -> navController.navigateToFeed(topLevelNavOptions)
            TopLevelDestination.BOOKMARK -> navController.navigateToBookmark(topLevelNavOptions)
            TopLevelDestination.CATEGORIES -> navController.navigateToFactByCategoryScreen(
                topLevelNavOptions
            )
        }
    }

    @Composable
    fun getNavigationTitle(): String? {
        return when {
            currentDestination?.hasRoute(SettingsScreenRoute::class) == true -> "Settings"
            currentDestination?.hasRoute(SearchScreenRoute::class) == true -> "Search"
            currentDestination?.hasRoute(FactsByCategoryScreenRoute::class) == true -> "Facts"
            else -> null
        }
    }

    fun navigateToSearch() = navController.navigateToSearch()
    fun navigateToSettings() = navController.navigateToSettings()
    fun navigateBack() = navController.popBackStack()
}