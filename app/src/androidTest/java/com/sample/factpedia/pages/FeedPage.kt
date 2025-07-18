package com.sample.factpedia.pages

import android.content.Context
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.sample.factpedia.features.feed.R

class FeedPage(context: Context, private val composeTestRule: ComposeTestRule) : Page(context) {

    private val feed = context.getString(R.string.feature_feed_title)
    private val feedTab = "tab_$feed"
    private val feedTitle = "title_$feed"

    override fun at() {
        composeTestRule.onNodeWithTag(feedTitle).assertExists()
    }

    fun isSelected() {
        composeTestRule.onNodeWithTag(feedTab).assertIsSelected()
    }

    fun performClick() {
        composeTestRule.onNodeWithTag(feedTab).performClick()
    }
}