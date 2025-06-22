package com.sample.factpedia.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.sample.factpedia.core.designsystem.icons.FactPediaIcons
import com.sample.factpedia.features.bookmarks.presentation.navigation.BookmarkScreenRoute
import com.sample.factpedia.features.categories.presentation.navigation.CategoryListScreenRoute
import com.sample.factpedia.features.feed.presentation.navigation.FeedScreenRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val text: String,
    val route: KClass<*>,
) {
    FEED(
        selectedIcon = FactPediaIcons.HomeFilled,
        unselectedIcon = FactPediaIcons.HomeOutlined,
        text = "Feed",
        route = FeedScreenRoute::class,
    ),

    BOOKMARK(
        selectedIcon = FactPediaIcons.BookMark,
        unselectedIcon = FactPediaIcons.BookMarkBorder,
        text = "Saved",
        route = BookmarkScreenRoute::class,
    ),

    CATEGORIES(
        selectedIcon = FactPediaIcons.CategoryFilled,
        unselectedIcon = FactPediaIcons.CategoryOutlined,
        text = "Categories",
        route = CategoryListScreenRoute::class,
    )
}