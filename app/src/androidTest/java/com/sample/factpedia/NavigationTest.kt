package com.sample.factpedia

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.sample.factpedia.features.search.R as SearchR
import com.sample.factpedia.features.settings.R as SettingsR
import com.sample.factpedia.features.feed.R as FeedR
import com.sample.factpedia.features.categories.R as CategoriesR
import com.sample.factpedia.features.bookmarks.R as BookmarkR

@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    private val feed by composeTestRule.stringResource(FeedR.string.feature_feed_title)
    private val categories by composeTestRule.stringResource(CategoriesR.string.feature_categories_title)
    private val bookmark by composeTestRule.stringResource(BookmarkR.string.feature_bookmark_title)
    private val search by composeTestRule.stringResource(SearchR.string.feature_search_title)
    private val settings by composeTestRule.stringResource(SettingsR.string.feature_settings_title)
    private lateinit var feedTab: String
    private lateinit var categoriesTab: String
    private lateinit var bookmarkTab: String
    private lateinit var searchTab: String
    private lateinit var settingsTab: String
    private lateinit var feedTitle: String
    private lateinit var categoriesTitle: String
    private lateinit var bookmarkTitle: String
    private lateinit var searchTitle: String
    private lateinit var settingsTitle: String

    private val navigateUp by composeTestRule.stringResource(R.string.back)

    @Before
    fun setup() {
        hiltRule.inject()
        feedTab = "tab_$feed"
        categoriesTab = "tab_$categories"
        bookmarkTab = "tab_$bookmark"
        searchTab = "tab_${search}"
        settingsTab = "tab_${settings}"
        feedTitle = "title_$feed"
        categoriesTitle = "title_$categories"
        bookmarkTitle = "title_$bookmark"
        searchTitle = "title_${search}"
        settingsTitle = "title_${settings}"
    }

    @Test
    fun firstScreen_isFeed() {
        composeTestRule.apply {
            onNodeWithTag(feedTitle).assertExists()
            onNodeWithTag(feedTab).assertIsSelected()
        }
    }

    @Test
    fun topLevelDestinations_doNotShowUpArrow() {
        composeTestRule.apply {
            onNodeWithTag(feedTitle).assertExists()
            onNodeWithTag(feedTab).assertIsSelected()
            onNodeWithContentDescription(navigateUp).assertDoesNotExist()

            onNodeWithTag(bookmarkTab).performClick()
            onNodeWithTag(bookmarkTitle).assertExists()
            onNodeWithTag(bookmarkTab).assertIsSelected()
            onNodeWithContentDescription(navigateUp).assertDoesNotExist()

            onNodeWithTag(categoriesTab).performClick()
            onNodeWithTag(categoriesTitle).assertExists()
            onNodeWithTag(categoriesTab).assertIsSelected()
            onNodeWithContentDescription(navigateUp).assertDoesNotExist()
        }
    }

    @Test
    fun topLevelDestinations_showSearchAndSettings() {
        composeTestRule.apply {
            onNodeWithTag(feedTitle).assertExists()
            onNodeWithTag(feedTab).assertIsSelected()
            onNodeWithContentDescription(searchTab).assertExists()
            onNodeWithContentDescription(settingsTab).assertExists()

            onNodeWithTag(bookmarkTab).performClick()
            onNodeWithTag(bookmarkTitle).assertExists()
            onNodeWithTag(bookmarkTab).assertIsSelected()
            onNodeWithContentDescription(searchTab).assertExists()
            onNodeWithContentDescription(settingsTab).assertExists()

            onNodeWithTag(categoriesTab).performClick()
            onNodeWithTag(categoriesTitle).assertExists()
            onNodeWithTag(categoriesTab).assertIsSelected()
            onNodeWithContentDescription(searchTab).assertExists()
            onNodeWithContentDescription(settingsTab).assertExists()
        }
    }

    @Test
    fun whenSearchIconIsClicked_searchScreenIsShown() {
        composeTestRule.apply {
            onNodeWithTag(feedTitle).assertExists()
            onNodeWithTag(feedTab).assertIsSelected()
            onNodeWithContentDescription(navigateUp).assertDoesNotExist()

            onNodeWithContentDescription(searchTab).assertExists().performClick()
            onNodeWithTag(searchTitle).assertExists()
            onNodeWithContentDescription(navigateUp).assertExists().performClick()

            onNodeWithTag(feedTitle).assertExists()
            onNodeWithTag(feedTab).assertIsSelected()
        }
    }

    @Test
    fun whenSettingsIconIsClicked_settingsScreenIsShown() {
        composeTestRule.apply {
            onNodeWithTag(feedTitle).assertExists()
            onNodeWithTag(feedTab).assertIsSelected()
            onNodeWithContentDescription(navigateUp).assertDoesNotExist()

            onNodeWithContentDescription(settingsTab).assertExists().performClick()
            onNodeWithTag(settingsTitle).assertExists()
            onNodeWithContentDescription(navigateUp).assertExists().performClick()

            onNodeWithTag(feedTitle).assertExists()
            onNodeWithTag(feedTab).assertIsSelected()
        }
    }

    @Test(expected = NoActivityResumedException::class)
    fun feedDestination_back_quitsApp() {
        composeTestRule.apply {
            onNodeWithTag(feedTitle).assertExists()
            onNodeWithTag(feedTab).assertIsSelected()
            Espresso.pressBack()
        }
    }

    @Test
    fun navigationBar_backFromAnyDestination_returnsToFeed() {
        composeTestRule.apply {
            onNodeWithTag(feedTitle).assertExists()
            onNodeWithTag(feedTab).assertIsSelected()

            onNodeWithTag(bookmarkTab).performClick()
            onNodeWithTag(bookmarkTab).assertIsSelected()

            onNodeWithTag(categoriesTab).performClick()
            onNodeWithTag(categoriesTab).assertIsSelected()

            Espresso.pressBack()

            onNodeWithTag(feedTitle).assertExists()
            onNodeWithTag(feedTab).assertIsSelected()
        }
    }
}