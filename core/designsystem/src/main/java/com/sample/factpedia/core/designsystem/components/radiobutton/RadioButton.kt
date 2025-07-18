package com.sample.factpedia.core.designsystem.components.radiobutton

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FactPediaRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
) {
    RadioButton(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colorScheme.onPrimary,
            unselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            disabledSelectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            disabledUnselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
    )
}