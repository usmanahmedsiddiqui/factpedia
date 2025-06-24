package com.sample.factpedia.core.designsystem.components.icon

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun FactPediaIconButton(
    icon: ImageVector,
    contentDescription: String = "",
    style: IconButtonStyle = IconButtonStyles.Primary,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            icon,
            contentDescription = contentDescription,
            tint = style.tintColor()
        )
    }
}