package com.sample.factpedia.features.categories

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.features.categories.presentation.state.FactsByCategoryScreenState
import com.sample.factpedia.features.categories.presentation.ui.FactsByCategoryScreen
import org.junit.Rule
import org.junit.Test
import com.sample.factpedia.core.common.R as Ln10R

class FactsByCategoryListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showsLoadingSpinner() {
        val categoryName = "Science"
        composeTestRule.setContent {
            FactsByCategoryScreen(
                categoryName = categoryName,
                state = FactsByCategoryScreenState(isLoading = true),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> },
            )
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(R.string.feature_facts_category_loading),
            )
            .assertExists()
    }


    @Test
    fun whenHasNoFacts_showsEmptyState() {
        val categoryName = "Science"
        composeTestRule.setContent {
            FactsByCategoryScreen(
                categoryName = categoryName,
                state = FactsByCategoryScreenState(emptyList()),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.feature_no_facts_found_in_category),
            )
            .assertExists()
    }

    @Test
    fun whenHasError_showsErrorState() {
        val categoryName = "Science"
        composeTestRule.setContent {
            FactsByCategoryScreen(
                categoryName = categoryName,
                state = FactsByCategoryScreenState(error = DataError.Network.TIMEOUT),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(Ln10R.string.error_timeout),
            )
            .assertExists()
    }

    @Test
    fun whenHasFacts_showsFacts() {
        val categoryName = "Science"
        val facts = listOf(
            BookmarkedFact(
                id = 1,
                fact = "Fact 1",
                categoryId = 1,
                categoryName = categoryName,
                isBookmarked = true,
            ),
            BookmarkedFact(
                id = 2,
                fact = "Fact 2",
                categoryId = 1,
                categoryName = categoryName,
                isBookmarked = false,
            ),
        )
        composeTestRule.setContent {
            FactsByCategoryScreen(
                categoryName = categoryName,
                state = FactsByCategoryScreenState(
                    facts
                ),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText(
                categoryName,
            )
            .assertExists()

        composeTestRule.onNodeWithText(
                facts[0].fact,
            )
            .assertExists()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    facts[1].fact,
                ),
            )
    }

    @Test
    fun whenHasError_clickRetry_callsOnRetryClicked() {
        var retryClicked = false
        val categoryName = "Science"
        composeTestRule.setContent {
            FactsByCategoryScreen(
                categoryName = categoryName,
                state = FactsByCategoryScreenState(error = DataError.Network.TIMEOUT),
                onRetryClicked = { retryClicked = true },
                onToggleBookmark = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithTag("retry_button")
            .performClick()

        assert(retryClicked)
    }

    @Test
    fun whenBookmarkClicked_callsOnToggleBookmark() {
        val categoryName = "Science"
        val bookmarkedFacts = listOf(
            BookmarkedFact(
                id = 1,
                fact = "Fact 1",
                categoryId = 1,
                categoryName = categoryName,
                isBookmarked = true,
            ),
        )

        val facts = mutableStateListOf(*bookmarkedFacts.toTypedArray())

        composeTestRule.setContent {
            FactsByCategoryScreen(
                categoryName = categoryName,
                state = FactsByCategoryScreenState(facts),
                onRetryClicked = {},
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
                categoryName,
            )
            .assertExists()

        composeTestRule
            .onNodeWithText(
                facts[0].fact,
            )
            .assertExists()

        composeTestRule
            .onNodeWithTag("bookmark_${bookmarkedFacts[0].id}")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("unbookmark_${bookmarkedFacts[0].id}")
            .assertExists()
    }
}