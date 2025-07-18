package com.sample.factpedia.pages

import android.content.Context
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.sample.factpedia.features.bookmarks.R

class BookmarkPage(context: Context, private val composeTestRule: ComposeTestRule) : Page(context) {

    private val bookmark = context.getString(R.string.feature_bookmark_title)
    private val  bookmarkTitle = "title_$bookmark"
    private val bookmarkTab = "tab_$bookmark"

    override fun at() {
        composeTestRule.onNodeWithTag(bookmarkTitle).assertExists()
    }

    fun isSelected() {
        composeTestRule.onNodeWithTag(bookmarkTab).assertIsSelected()
    }

    fun performClick() {
        composeTestRule.onNodeWithTag(bookmarkTab).performClick()
    }
}