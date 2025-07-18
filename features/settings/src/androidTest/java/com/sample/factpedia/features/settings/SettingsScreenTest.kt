package com.sample.factpedia.features.settings

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.sample.factpedia.core.model.domain.ThemePreference
import com.sample.factpedia.features.settings.presentation.ui.SettingsScreen
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun rendersAllThemeOptions_andMarksCurrentSelection() {
        val currentTheme = ThemePreference.DARK

        composeTestRule.setContent {
            SettingsScreen(
                currentTheme = currentTheme,
                onThemeChanged = {}
            )
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.feature_choose_theme)
        ).assertExists()

        ThemePreference.entries.forEach { preference ->
            val displayName = preference.name.lowercase().replaceFirstChar { it.uppercaseChar() }

            composeTestRule.onNodeWithText(displayName).assertExists()
        }

        composeTestRule
            .onNodeWithTag("theme_radio_${currentTheme.name.lowercase()}")
            .assertIsSelected()
    }

    @Test
    fun whenThemeClicked_callsOnThemeChanged() {
        var selectedTheme: ThemePreference = ThemePreference.SYSTEM

        composeTestRule.setContent {
            SettingsScreen(
                currentTheme = selectedTheme,
                onThemeChanged = { selectedTheme = it }
            )
        }

        val targetTheme = ThemePreference.DARK
        val displayName = targetTheme.name.lowercase().replaceFirstChar { it.uppercaseChar() }

        composeTestRule.onNodeWithText(displayName).performClick()

        assert(selectedTheme == targetTheme)
    }
}