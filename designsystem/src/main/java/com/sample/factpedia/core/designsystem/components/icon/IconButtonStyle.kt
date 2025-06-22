package com.sample.factpedia.core.designsystem.components.icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

object IconButtonStyles {
    val Primary =
        object : IconButtonStyle {
            @Composable
            override fun tintColor(): Color = MaterialTheme.colorScheme.primary
        }
}

@Stable
interface IconButtonStyle {
    @Composable
    fun tintColor(): Color
}
