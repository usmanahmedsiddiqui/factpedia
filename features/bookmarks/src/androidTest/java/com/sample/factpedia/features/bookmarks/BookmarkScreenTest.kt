package com.sample.factpedia.features.bookmarks

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.features.bookmarks.presentation.state.BookmarkScreenState
import com.sample.factpedia.features.bookmarks.presentation.ui.BookmarksScreen
import org.junit.Rule
import org.junit.Test

class BookmarkScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun whenHasBookmarks_showsBookmarks() {
        composeTestRule.setContent {
            BookmarksScreen(
                state = BookmarkScreenState(bookmarkedFact),
                onBookmarkRemoved = { },
            )
        }

        composeTestRule
            .onNodeWithText(
                bookmarkedFact[0].fact,
            )
            .assertExists()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    bookmarkedFact[1].fact,
                ),
            )

        composeTestRule
            .onNodeWithText(
                bookmarkedFact[2].fact,
            )
            .assertExists()
    }

    @Test
    fun whenRemovingBookmark_removesBookmark() {
        val facts = mutableStateListOf(*bookmarkedFact.toTypedArray())
        composeTestRule.setContent {
            BookmarksScreen(
                state = BookmarkScreenState(facts),
                onBookmarkRemoved = { id ->
                    facts.removeAll { it.id == id }
                },
            )
        }

        composeTestRule.onNodeWithText(bookmarkedFact[0].fact).assertExists()
        composeTestRule.onNodeWithText(bookmarkedFact[1].fact).assertExists()
        composeTestRule.onNodeWithText(bookmarkedFact[2].fact).assertExists()

        composeTestRule
            .onNodeWithTag("bookmark_${bookmarkedFact[0].id}")
            .performClick()

        // Confirm Fact 1 is no longer shown
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(bookmarkedFact[0].fact).assertDoesNotExist()
    }

    @Test
    fun whenHasNoBookmarks_showsEmptyState() {
        composeTestRule.setContent {
            BookmarksScreen(
                state = BookmarkScreenState(emptyList()),
                onBookmarkRemoved = {},
            )
        }

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.feature_bookmark_not_found),
            )
            .assertExists()
    }
}

private val bookmarkedFact = listOf(
    BookmarkedFact(
        id = 1,
        fact = "Fact 1",
        categoryName = "Category 1",
        categoryId = 1,
        isBookmarked = true
    ),

    BookmarkedFact(
        id = 2,
        fact = "Fact 2",
        categoryName = "Category 2",
        categoryId = 2,
        isBookmarked = true
    ),

    BookmarkedFact(
        id = 3,
        fact = "Fact 3",
        categoryName = "Category 3",
        categoryId = 3,
        isBookmarked = true
    )
)