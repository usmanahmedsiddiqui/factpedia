package com.sample.factpedia.pages

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.sample.factpedia.features.settings.R

class SettingsPage(context: Context, private val composeTestRule: ComposeTestRule) : Page(context) {
    private val settings = context.getString(R.string.feature_settings_title)
    private val settingsTab = "tab_$settings"
    private val settingsTitle = "title_$settings"

    override fun at() {
        composeTestRule.onNodeWithTag(settingsTitle).assertExists()
    }

    fun performClick() {
        composeTestRule.onNodeWithContentDescription(settingsTab).performClick()
    }

    fun navigationShown() {
        composeTestRule.onNodeWithContentDescription(settingsTab).assertExists()
    }
}