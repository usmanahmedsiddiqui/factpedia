package com.sample.factpedia.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class SemanticColors(
    val content: Content,
    val background: Background,
    val border: Border,
)

@Immutable
data class Content(
    val accent: Color,
    val brand: Color,
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val invertedPrimary: Color,
    val invertedSecondary: Color,
    val positive: Color,
    val negative: Color,
    val negativeStrong: Color,
    val attention: Color,
)

@Immutable
data class Background(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val invertedPrimary: Color,
    val invertedSecondary: Color,
    val overlay: Color,
    val overlayLight: Color,
    val overlayAccent: Color,
    val neutral: Color,
    val positive: Color,
    val positiveLight: Color,
    val negative: Color,
    val negativeLight: Color,
    val negativeMedium: Color,
    val negativeStrong: Color,
    val accent: Color,
    val accentLight: Color,
    val accentMedium: Color,
    val accentAction: Color,
    val attention: Color,
    val incentive: Color,
    val incentiveLight: Color,
    val brand: Color,
)

@Immutable
data class Border(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val invertedPrimary: Color,
    val invertedSecondary: Color,
    val positive: Color,
    val positiveLight: Color,
    val negative: Color,
    val negativeLight: Color,
    val accent: Color,
    val accentLight: Color,
)
