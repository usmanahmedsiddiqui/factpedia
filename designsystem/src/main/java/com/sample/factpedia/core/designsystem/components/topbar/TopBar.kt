package com.sample.factpedia.core.designsystem.components.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.sample.factpedia.core.designsystem.components.text.FactPediaText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactPediaNavigationTopBar(
    text: String,
    navigation: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        title = {
            FactPediaText(
                text = text,
                textStyle = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            navigation?.invoke()
        },
        actions = {
            actions?.invoke(this)
        }
    )
}