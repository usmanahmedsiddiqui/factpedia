package com.sample.factpedia.pages

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText

class FactByCategoryPage(private val composeTestRule: ComposeTestRule) {
    fun at(categoryName: String, facts: List<String>) {
        composeTestRule.apply {
            waitUntil(timeoutMillis = 5_000) {
                onAllNodesWithText(categoryName).fetchSemanticsNodes().isNotEmpty()
                val allFactsExist = facts.all { fact ->
                    onAllNodesWithText(fact).fetchSemanticsNodes().isNotEmpty()
                }
                allFactsExist
            }
        }
    }
}