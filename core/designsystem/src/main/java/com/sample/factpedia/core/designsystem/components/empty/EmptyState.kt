package com.sample.factpedia.core.designsystem.components.empty

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.sample.factpedia.core.designsystem.components.text.FactPediaText

@Composable
fun FactPediaEmptyMessage(
    text: String
) {
    FactPediaText(
        text = text,
        textStyle = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimary,
    )
}