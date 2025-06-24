package com.sample.factpedia.features.settings.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.factpedia.core.designsystem.components.radiobutton.FactPediaRadioButton
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.datastore.ThemePreference
import com.sample.factpedia.features.settings.presentation.actions.SettingsScreenAction
import com.sample.factpedia.features.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacings.spacing16)
    ) {
        val currentTheme = viewModel.state.collectAsState().value
        FactPediaText(
            text = "Choose Theme",
            textStyle = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(Spacings.spacing8))

        ThemePreference.entries.forEach { preference ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.onAction(SettingsScreenAction.ThemeChanged(preference)) }
                    .padding(8.dp)
            ) {
                FactPediaRadioButton(
                    selected = preference == currentTheme,
                    onClick = { viewModel.onAction(SettingsScreenAction.ThemeChanged(preference)) }
                )

                FactPediaText(
                    text = preference.name.lowercase().replaceFirstChar { it.uppercaseChar() },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}