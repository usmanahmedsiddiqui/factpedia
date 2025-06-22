package com.sample.factpedia.features.feed.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sample.factpedia.features.feed.presentation.ui.FeedScreen
import kotlinx.serialization.Serializable

@Serializable
object FeedScreenRoute

fun NavHostController.navigateToFeed(navOptions: NavOptions) {
    this.navigate(FeedScreenRoute, navOptions)
}

fun NavGraphBuilder.feedScreen() {
    composable<FeedScreenRoute> { FeedScreen() }
}