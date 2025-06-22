package com.sample.factpedia.features.settings.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(){
    Text(
        text = "Settings Screen",
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(top = 16.dp)
    )
}