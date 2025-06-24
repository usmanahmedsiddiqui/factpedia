package com.sample.factpedia.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    surface = SurfaceLight,
    error = ErrorLight,
    onError = OnErrorLight,
)

private val DarkColors = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    surface = SurfaceDark,
    error = ErrorDark,
    onError = OnErrorDark,
)

@Composable
fun FactPediaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = FactPediaTypography,
        content = content
    )
}

@Composable
fun FactPediaGradientBackground(
    modifier: Modifier = Modifier,
    isDark: Boolean,
    content: @Composable () -> Unit
) {
    val gradientColors = if (isDark) {
        listOf(
            GradientLight1,
            GradientLight2,
            GradientLight3,
            GradientLight4,
        )
    } else {
        listOf(
            GradientDark1,
            GradientDark2,
            GradientDark3,
            GradientDark4,
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = gradientColors)
            )
    ) {
        content()
    }
}
