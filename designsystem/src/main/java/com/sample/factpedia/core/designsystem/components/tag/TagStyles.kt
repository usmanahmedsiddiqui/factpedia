package com.sample.factpedia.core.designsystem.components.tag

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

object TagStyles {
    val Primary =
        object : TagStyle {
            @Composable
            override fun backgroundColor(): Color = MaterialTheme.colorScheme.primaryContainer

            @Composable
            override fun textStyle(): TextStyle {
                return createTextStyle(
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            @Composable
            override fun imageResourceColorFilter(): ColorFilter {
                return ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
}

private fun createTextStyle(color: Color) = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    letterSpacing = 0.03.em,
    color = color,
    fontFamily = FontFamily.SansSerif,
    platformStyle = PlatformTextStyle(includeFontPadding = true),
)

@Stable
interface TagStyle {
    @Composable
    fun backgroundColor(): Color

    @Composable
    fun textStyle(): TextStyle

    @Composable
    fun imageResourceColorFilter(): ColorFilter
}
