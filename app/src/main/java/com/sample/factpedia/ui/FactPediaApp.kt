package com.sample.factpedia.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import com.sample.factpedia.core.designsystem.components.icon.FactPediaIconButton
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.components.topbar.FactPediaNavigationTopBar
import com.sample.factpedia.core.designsystem.icons.FactPediaIcons
import com.sample.factpedia.navigation.FactPediaNavHost

@Composable
fun FactPediaApp(
    appState: FactPediaAppState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            val topLevelDestination = appState.currentTopLevelDestination
            val currentDestinationTitle = appState.getNavigationTitle()
            if (topLevelDestination != null) {
                FactPediaNavigationTopBar(
                    text = topLevelDestination.text,
                    navigation = {
                        FactPediaIconButton(
                            icon = FactPediaIcons.Search,
                            contentDescription = "Search",
                            onClick = { appState.navigateToSearch() }
                        )
                    },
                    actions = {
                        FactPediaIconButton(
                            icon = FactPediaIcons.Settings,
                            contentDescription = "Settings",
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
                            contentDescription = "Back",
                            onClick = { appState.navigateBack() }
                        )
                    },
                )
            }
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

@Composable
fun FactPediaBottomBar(appState: FactPediaAppState) {
    NavigationBar {
        appState.topLevelDestinations.forEach { destination ->
            val selected = appState.currentDestination?.hasRoute(destination.route) == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    appState.navigateToTopLevelDestination(destination)
                },
                icon = {
                    Icon(
                        imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                        contentDescription = destination.text
                    )
                },
                label = {
                    FactPediaText(
                        text = destination.text,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    }
}