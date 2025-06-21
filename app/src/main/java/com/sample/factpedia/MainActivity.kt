package com.sample.factpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.sample.factpedia.features.bookmarks.presentation.ui.BookmarkScreen
import com.sample.factpedia.features.categories.presentation.ui.CategoryListScreen
import com.sample.factpedia.features.categories.presentation.ui.FactsByCategoryScreen
import com.sample.factpedia.features.feed.presentation.ui.FeedScreen
import com.sample.factpedia.features.search.presentation.ui.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentRoute = currentDestination?.route

    val bottomNavRoutes = TopLevelDestination.entries.map { it.route }
    val isBottomNavScreen = bottomNavRoutes.any { currentDestination?.hasRoute(it) == true }
    val currentTopLevelDestination: TopLevelDestination? =
        TopLevelDestination.entries.firstOrNull { topLevelDestination ->
            currentDestination?.hasRoute(route = topLevelDestination.route) == true
        }

    Scaffold(
        topBar = {
            if (currentTopLevelDestination != null) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(currentTopLevelDestination.label)
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(SearchScreenRoute) }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate(SettingScreenRoute) }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (isBottomNavScreen) {
                NavigationBar {
                    TopLevelDestination.entries.forEach { destination ->
                        val selected = destination.route.simpleName == currentRoute
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                val topLevelNavOptions = navOptions {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }

                                when(destination) {
                                    TopLevelDestination.FEED -> navController.navigate(FeedScreenRoute, topLevelNavOptions)
                                    TopLevelDestination.BOOKMARK -> navController.navigate(BookmarkScreenRoute, topLevelNavOptions)
                                    TopLevelDestination.CATEGORIES -> navController.navigate(CategoryListScreenRoute, topLevelNavOptions)
                                }
                            },
                            icon = {
                                Icon(
                                    destination.icon,
                                    contentDescription = destination.label
                                )
                            },
                            label = { Text(destination.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FeedScreenRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<FeedScreenRoute> { FeedScreen() }
            composable<BookmarkScreenRoute> { BookmarkScreen() }
            composable<CategoryListScreenRoute> {
                CategoryListScreen(
                    onCategoryClick = { category ->
                        navController.navigate(FactsByCategoryScreenRoute(category.id, category.name))
                    }
                )
            }
            composable<FactsByCategoryScreenRoute> {
                val args = it.toRoute<FactsByCategoryScreenRoute>()
                FactsByCategoryScreen(args.categoryId, args.categoryName)
            }
            composable<SearchScreenRoute> { SearchScreen() }
        }
    }
}

enum class TopLevelDestination(
    val icon: ImageVector,
    val label: String,
    val route: KClass<*>,
) {
    FEED(
        icon = Icons.Default.Home,
        label = "Feed",
        route = FeedScreenRoute::class,
    ),

    BOOKMARK(
        icon = Icons.Default.Bookmark,
        label = "Saved",
        route = BookmarkScreenRoute::class,
    ),

    CATEGORIES(
        icon = Icons.Default.Category,
        label = "Categories",
        route = CategoryListScreenRoute::class,
    )
}

@Serializable
object FeedScreenRoute

@Serializable
object BookmarkScreenRoute

@Serializable
object CategoryListScreenRoute

@Serializable
data class FactsByCategoryScreenRoute(val categoryId: Int, val categoryName: String)

@Serializable
object SearchScreenRoute

@Serializable
object SettingScreenRoute