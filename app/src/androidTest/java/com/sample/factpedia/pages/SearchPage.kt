package com.sample.factpedia.pages

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.sample.factpedia.features.search.R

class SearchPage(context: Context, private val composeTestRule: ComposeTestRule) : Page(context) {

    private val search = context.getString(R.string.feature_search_title)
    private val searchTab: String = "tab_$search"
    private val searchTitle: String = "title_$search"

    override fun at() {
        composeTestRule.onNodeWithTag(searchTitle).assertExists()
    }

    fun performClick() {
        composeTestRule.onNodeWithContentDescription(searchTab).performClick()
    }

    fun navigationShown() {
        composeTestRule.onNodeWithContentDescription(searchTab).assertExists()
    }
}