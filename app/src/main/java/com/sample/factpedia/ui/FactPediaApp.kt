package com.sample.factpedia.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import com.sample.factpedia.R
import com.sample.factpedia.core.designsystem.components.icon.FactPediaIconButton
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.components.topbar.FactPediaNavigationTopBar
import com.sample.factpedia.core.designsystem.icons.FactPediaIcons
import com.sample.factpedia.core.designsystem.theme.FactPediaGradientBackground
import com.sample.factpedia.navigation.FactPediaNavHost
import com.sample.factpedia.features.search.R as SearchR
import com.sample.factpedia.features.settings.R as SettingsR

@Composable
fun FactPediaApp(
    appState: FactPediaAppState,
    isDark: Boolean,
    modifier: Modifier = Modifier,
) {
    FactPediaGradientBackground(isDark = isDark) {
        Scaffold(
            containerColor = Color.Transparent,
            modifier = modifier,
            topBar = {
                FactPediaTopBar(appState)
            },
            bottomBar = {
                val topLevelDestination = appState.currentTopLevelDestination
                if (topLevelDestination != null) {
                    FactPediaBottomBar(appState)
                }
            }
        ) { innerPadding ->
            FactPediaNavHost(
                appState.navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun FactPediaTopBar(appState: FactPediaAppState) {
    val topLevelDestination = appState.currentTopLevelDestination
    val currentDestinationTitle = appState.getNavigationTitle()
    if (topLevelDestination != null) {
        FactPediaNavigationTopBar(
            text = stringResource(topLevelDestination.textId),
            navigation = {
                FactPediaIconButton(
                    icon = FactPediaIcons.Search,
                    contentDescription = "tab_${stringResource(SearchR.string.feature_search_title)}",
                    onClick = { appState.navigateToSearch() }
                )
            },
            actions = {
                FactPediaIconButton(
                    icon = FactPediaIcons.Settings,
                    contentDescription = "tab_${stringResource(SettingsR.string.feature_settings_title)}",
                    onClick = { appState.navigateToSettings() }
                )
            },
        )
    } else if (currentDestinationTitle != null) {
        FactPediaNavigationTopBar(
            text = currentDestinationTitle,
            navigation = {
                FactPediaIconButton(
                    icon = FactPediaIcons.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    onClick = { appState.navigateBack() }
                )
            },
        )
    }
}

@Composable
fun FactPediaBottomBar(appState: FactPediaAppState) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        appState.topLevelDestinations.forEach { destination ->
            val selected = appState.currentDestination?.hasRoute(destination.route) == true
            NavigationBarItem(
                modifier = Modifier.testTag("tab_${stringResource(destination.textId)}"),
                selected = selected,
                onClick = {
                    appState.navigateToTopLevelDestination(destination)
                },
                icon = {
                    Icon(
                        imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                        contentDescription = stringResource(destination.textId),
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                label = {
                    FactPediaText(
                        text = stringResource(destination.textId),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        }
    }
}