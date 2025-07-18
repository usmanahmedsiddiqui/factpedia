package com.sample.factpedia.core.designsystem.components.icon

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun FactPediaIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String = "",
    style: IconButtonStyle = IconButtonStyles.Primary,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            icon,
            contentDescription = contentDescription,
            tint = style.tintColor()
        )
    }
}