package com.sample.factpedia.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.sample.factpedia.core.designsystem.icons.FactPediaIcons
import com.sample.factpedia.features.bookmarks.presentation.navigation.BookmarkScreenRoute
import com.sample.factpedia.features.categories.presentation.navigation.CategoryListScreenRoute
import com.sample.factpedia.features.feed.presentation.navigation.FeedScreenRoute
import kotlin.reflect.KClass
import com.sample.factpedia.features.feed.R as FeedR
import com.sample.factpedia.features.categories.R as CategoriesR
import com.sample.factpedia.features.bookmarks.R as BookmarkR

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val textId: Int,
    val route: KClass<*>,
) {
    FEED(
        selectedIcon = FactPediaIcons.HomeFilled,
        unselectedIcon = FactPediaIcons.HomeOutlined,
        textId = FeedR.string.feature_feed_title,
        route = FeedScreenRoute::class,
    ),

    BOOKMARK(
        selectedIcon = FactPediaIcons.BookMark,
        unselectedIcon = FactPediaIcons.BookMarkBorder,
        textId = BookmarkR.string.feature_bookmark_title,
        route = BookmarkScreenRoute::class,
    ),

    CATEGORIES(
        selectedIcon = FactPediaIcons.CategoryFilled,
        unselectedIcon = FactPediaIcons.CategoryOutlined,
        textId = CategoriesR.string.feature_categories_title,
        route = CategoryListScreenRoute::class,
    )
}