package com.sample.factpedia.core.designsystem.components.button

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sample.factpedia.core.designsystem.theme.Spacings

object ButtonSizes {
    val Small =
        object : ButtonSize {
            @Composable
            override fun textStyle() = MaterialTheme.typography.bodySmall

            override fun iconSize(): Dp = Spacings.spacing16

            override fun iconPadding(): Dp = Spacings.spacing8

            override fun minHeight(): Dp = 48.dp

            override fun toString() = "Small"
        }
    val Medium =
        object : ButtonSize {
            @Composable
            override fun textStyle() = MaterialTheme.typography.bodyMedium

            override fun iconSize(): Dp = 20.dp

            override fun iconPadding(): Dp = Spacings.spacing8

            override fun minHeight(): Dp = 56.dp

            override fun toString() = "Medium"
        }
    val Large =
        object : ButtonSize {
            @Composable
            override fun textStyle() = MaterialTheme.typography.bodyLarge

            override fun iconSize(): Dp = Spacings.spacing24

            override fun iconPadding(): Dp = Spacings.spacing12

            override fun minHeight(): Dp = 64.dp

            override fun toString() = "Large"
        }
}

@Stable
interface ButtonSize {
    @Composable
    fun textStyle(): TextStyle

    fun iconSize(): Dp

    fun iconPadding(): Dp

    fun minHeight(): Dp
}
