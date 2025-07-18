package com.sample.factpedia.pages

import android.content.Context
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.sample.factpedia.features.categories.R

class CategoryPage(context: Context, private val composeTestRule: ComposeTestRule) : Page(context) {

    private val categories = context.getString(R.string.feature_categories_title)
    private val categoriesTitle = "title_$categories"
    private val categoriesTab = "tab_$categories"

    override fun at() {
        composeTestRule.onNodeWithTag(categoriesTitle).assertExists()
    }

    fun isSelected() {
        composeTestRule.onNodeWithTag(categoriesTab).assertIsSelected()
    }

    fun performClick() {
        composeTestRule.onNodeWithTag(categoriesTab).performClick()
    }

    fun isCategoryShown(categoryName: String) {
        composeTestRule.onNodeWithText(categoryName).assertExists()
    }

    fun areCategoriesShown(categories: List<String>) {
        composeTestRule.apply {
            waitUntil(timeoutMillis = 5_000) {
                val allCategoriesExist = categories.all { category ->
                    onAllNodesWithText(category).fetchSemanticsNodes().isNotEmpty()
                }
                allCategoriesExist
            }
        }
    }

    fun clickCategory(categoryName: String) {
        composeTestRule.onNodeWithText(categoryName).performClick()
    }
}