package com.sample.factpedia.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import com.sample.factpedia.navigation.FactPediaNavHost
import com.sample.factpedia.navigation.TopLevelDestination
import com.sample.factpedia.ui.theme.FactPediaAppState

@Composable
fun FactPediaApp(
    appState: FactPediaAppState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            val destination = appState.currentTopLevelDestination
            if (destination != null) {
                FPTopBar(
                    destination = destination,
                    onNavigationClick = { appState.navigateToSearch() },
                    onActionClick = { appState.navigateToSettings() }
                )
            }
        },
        bottomBar = {
            val destination = appState.currentTopLevelDestination
            if (destination != null) {
                FPBottomBar(appState)
            }
        }
    ) { innerPadding ->
        FactPediaNavHost(
            appState.navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FPTopBar(
    destination: TopLevelDestination,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(destination.label)
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    )
}

@Composable
fun FPBottomBar(appState: FactPediaAppState) {
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
                        contentDescription = destination.label
                    )
                },
                label = { Text(destination.label) }
            )
        }

    }
}