package com.sample.factpedia.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.sample.factpedia.features.bookmarks.presentation.navigation.BookmarkScreenRoute
import com.sample.factpedia.features.categories.presentation.navigation.CategoryListScreenRoute
import com.sample.factpedia.features.feed.presentation.navigation.FeedScreenRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
    val route: KClass<*>,
) {
    FEED(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = "Feed",
        route = FeedScreenRoute::class,
    ),

    BOOKMARK(
        selectedIcon = Icons.Default.Bookmark,
        unselectedIcon = Icons.Default.BookmarkBorder,
        label = "Saved",
        route = BookmarkScreenRoute::class,
    ),

    CATEGORIES(
        selectedIcon = Icons.Filled.Category,
        unselectedIcon = Icons.Outlined.Category,
        label = "Categories",
        route = CategoryListScreenRoute::class,
    )
}