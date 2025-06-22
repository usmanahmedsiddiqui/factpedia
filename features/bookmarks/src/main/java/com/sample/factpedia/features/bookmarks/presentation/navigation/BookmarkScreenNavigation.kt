package com.sample.factpedia.features.bookmarks.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sample.factpedia.features.bookmarks.presentation.ui.BookmarkScreen
import kotlinx.serialization.Serializable

@Serializable
object BookmarkScreenRoute

fun NavHostController.navigateToBookmark(navOptions: NavOptions) {
    this.navigate(BookmarkScreenRoute, navOptions)
}

fun NavGraphBuilder.bookmarksScreen() {
    composable<BookmarkScreenRoute> { BookmarkScreen() }
}


