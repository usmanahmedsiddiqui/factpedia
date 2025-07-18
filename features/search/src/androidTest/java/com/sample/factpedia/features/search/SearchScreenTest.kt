package com.sample.factpedia.features.search

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.features.search.presentation.state.SearchScreenState
import com.sample.factpedia.features.search.presentation.ui.SearchScreen
import org.junit.Rule
import org.junit.Test
import com.sample.factpedia.core.common.R as Ln10R

class SearchScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun typingInSearchBar_updatesState() {
        val searchState = mutableStateOf(SearchScreenState(query = ""))
        val testQuery = "space"

        composeTestRule.setContent {
            SearchScreen(
                state = searchState.value,
                onClearSearch = {},
                onTriggerSearch = { query ->
                    searchState.value = searchState.value.copy(query = query)
                },
                onToggleBookmark = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.feature_search_place_holder),
                substring = true
            )
            .performTextInput(testQuery)

        composeTestRule.waitForIdle()

        composeTestRule.runOnIdle {
            assert(searchState.value.query == testQuery)
        }
    }

    @Test
    fun clickingClearButton_clearsQueryState() {
        val searchState = mutableStateOf(SearchScreenState(query = "moon"))

        composeTestRule.setContent {
            SearchScreen(
                state = searchState.value,
                onClearSearch = {
                    searchState.value = searchState.value.copy(query = "")
                },
                onTriggerSearch = {},
                onToggleBookmark = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Clear")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.runOnIdle {
            assert(searchState.value.query.isEmpty())
        }
    }

    @Test
    fun showsSearchResults_whenAvailable() {
        val facts = listOf(
            BookmarkedFact(
                id = 1,
                fact = "Fact 1",
                categoryId = 1,
                categoryName = "Science",
                isBookmarked = false
            )
        )

        composeTestRule.setContent {
            SearchScreen(
                state = SearchScreenState(query = "space", searchResults = facts),
                onClearSearch = {},
                onTriggerSearch = {},
                onToggleBookmark = { _, _ -> }
            )
        }

        composeTestRule.onNodeWithText("Fact 1").assertExists()
    }

    @Test
    fun showsError_whenSearchFails() {
        composeTestRule.setContent {
            SearchScreen(
                state = SearchScreenState(
                    query = "space",
                    error = DataError.Network.TIMEOUT
                ),
                onClearSearch = {},
                onTriggerSearch = {},
                onToggleBookmark = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(Ln10R.string.error_timeout)
            )
            .assertExists()
    }

    @Test
    fun clickingBookmark_updatesBookmarkState() {
        val bookmarkedFacts = listOf(
            BookmarkedFact(
                id = 1,
                fact = "Fact 1",
                categoryId = 1,
                categoryName = "Science",
                isBookmarked = true
            )
        )

        val facts = mutableStateListOf(*bookmarkedFacts.toTypedArray())

        composeTestRule.setContent {
            SearchScreen(
                state = SearchScreenState(searchResults = facts),
                onClearSearch = {},
                onTriggerSearch = {},
                onToggleBookmark = { factId, isBookmarked ->
                    val index = facts.indexOfFirst { it.id == factId }
                    if (index != -1) {
                        facts[index] = facts[index].copy(isBookmarked = isBookmarked)
                    }
                }
            )
        }

        composeTestRule
            .onNodeWithText(
                facts[0].fact,
            ).assertExists()

        composeTestRule
            .onNodeWithTag("bookmark_${bookmarkedFacts[0].id}")
            .assertExists()
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("unbookmark_${bookmarkedFacts[0].id}")
            .assertExists()
    }
}