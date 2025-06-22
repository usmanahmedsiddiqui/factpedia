package com.sample.factpedia.features.settings.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sample.factpedia.features.settings.presentation.ui.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object SettingsScreenRoute

fun NavHostController.navigateToSettings() {
    this.navigate(SettingsScreenRoute)
}

fun NavGraphBuilder.settingsScreen() {
    composable<SettingsScreenRoute> { SettingsScreen() }
}
