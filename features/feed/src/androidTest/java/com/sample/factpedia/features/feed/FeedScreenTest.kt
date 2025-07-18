package com.sample.factpedia.features.feed

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.features.feed.presentation.state.FeedScreenState
import com.sample.factpedia.features.feed.presentation.ui.FeedScreen
import org.junit.Rule
import org.junit.Test
import com.sample.factpedia.core.common.R as Ln10R

class FeedScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showsLoadingSpinner_whenIsLoading() {
        composeTestRule.setContent {
            FeedScreen(
                state = FeedScreenState(isLoading = true),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> },
                onNextFact = {}
            )
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.feature_feed_loading)
        ).assertExists()
    }

    @Test
    fun showsErrorMessage_whenHasError() {
        composeTestRule.setContent {
            FeedScreen(
                state = FeedScreenState(error = DataError.Network.TIMEOUT),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> },
                onNextFact = {}
            )
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(Ln10R.string.error_timeout)
        ).assertExists()
    }

    @Test
    fun whenRetryClicked_invokesOnRetryClicked() {
        var retried = false

        composeTestRule.setContent {
            FeedScreen(
                state = FeedScreenState(error = DataError.Network.TIMEOUT),
                onRetryClicked = { retried = true },
                onToggleBookmark = { _, _ -> },
                onNextFact = {}
            )
        }

        composeTestRule.onNodeWithTag("retry_button").performClick()

        assert(retried)
    }

    @Test
    fun showsEmptyState_whenNoFactAndNotLoadingOrError() {
        composeTestRule.setContent {
            FeedScreen(
                state = FeedScreenState(),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> },
                onNextFact = {}
            )
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.feature_feed_no_fact_found)
        ).assertExists()
    }

    @Test
    fun showsFactCard_whenFactIsAvailable() {
        val fact = BookmarkedFact(
            id = 1,
            fact = "Fact 1",
            categoryId = 1,
            categoryName = "Science",
            isBookmarked = false
        )

        composeTestRule.setContent {
            FeedScreen(
                state = FeedScreenState(fact = fact),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> },
                onNextFact = {}
            )
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.feature_feed_did_you_know)
        ).assertExists()

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.feature_feed_next_fact)
        ).assertExists()

        composeTestRule.onNodeWithText(
            fact.fact
        ).assertExists()
    }

    @Test
    fun whenBookmarkClicked_invokesOnToggleBookmark() {
        val fact = BookmarkedFact(
            id = 1,
            fact = "Fact 1",
            categoryId = 1,
            categoryName = "Science",
            isBookmarked = true
        )

        val bookmarkedFact = mutableStateOf(fact)

        composeTestRule.setContent {
            FeedScreen(
                state = FeedScreenState(fact = bookmarkedFact.value),
                onRetryClicked = {},
                onToggleBookmark = { _, isBookmarked ->
                    bookmarkedFact.value = bookmarkedFact.value.copy(isBookmarked = isBookmarked)
                },
                onNextFact = {}
            )
        }

        composeTestRule.onNodeWithText(
            fact.fact
        ).assertExists()

        composeTestRule.onNodeWithTag(
            "bookmark_${fact.id}"
        ).performClick()

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("unbookmark_${fact.id}")
            .assertExists()
    }

    @Test
    fun whenNextFactClicked_invokesOnNextFact() {
        val fact1 = BookmarkedFact(
            id = 1,
            fact = "Fact 1",
            categoryId = 1,
            categoryName = "Science",
            isBookmarked = false
        )
        val fact2 = BookmarkedFact(
            id = 2,
            fact = "Fact 2",
            categoryId = 1,
            categoryName = "Science",
            isBookmarked = false
        )

        val currentFact = mutableStateOf(fact1)

        composeTestRule.setContent {
            FeedScreen(
                state = FeedScreenState(fact = currentFact.value),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> },
                onNextFact = { currentFact.value = fact2 }
            )
        }

        composeTestRule.onNodeWithText(fact1.fact).assertExists()

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.feature_feed_next_fact)
        ).performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(fact1.fact).assertDoesNotExist()
        composeTestRule.onNodeWithText(fact2.fact).assertExists()
    }
}