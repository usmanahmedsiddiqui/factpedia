package com.sample.factpedia.tests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import com.sample.factpedia.MainActivity
import com.sample.factpedia.R
import com.sample.factpedia.core.network.di.NetworkModule
import com.sample.factpedia.pages.BookmarkPage
import com.sample.factpedia.pages.CategoryPage
import com.sample.factpedia.pages.FeedPage
import com.sample.factpedia.pages.SearchPage
import com.sample.factpedia.pages.SettingsPage
import com.sample.factpedia.util.dispatchMockResponses
import com.sample.factpedia.util.stringResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class NavigationTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var mockWebServer: MockWebServer

    private lateinit var feedPage: FeedPage
    private lateinit var categoryPage: CategoryPage
    private lateinit var bookmarkPage: BookmarkPage
    private lateinit var settingsPage: SettingsPage
    private lateinit var searchPage: SearchPage

    private val navigateUp by composeTestRule.stringResource(R.string.back)

    @Before
    fun setup() {
        hiltRule.inject()
        feedPage = FeedPage(composeTestRule.activity, composeTestRule)
        categoryPage = CategoryPage(composeTestRule.activity, composeTestRule)
        bookmarkPage = BookmarkPage(composeTestRule.activity, composeTestRule)
        settingsPage = SettingsPage(composeTestRule.activity, composeTestRule)
        searchPage = SearchPage(composeTestRule.activity, composeTestRule)

        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return dispatchMockResponses(request)
            }
        }
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun firstScreen_isFeed() {
        feedPage.at()
        feedPage.isSelected()
    }

    @Test
    fun topLevelDestinations_doNotShowUpArrow() {
        composeTestRule.apply {
            feedPage.at()
            feedPage.isSelected()

            onNodeWithContentDescription(navigateUp).assertDoesNotExist()

            bookmarkPage.performClick()
            bookmarkPage.at()
            bookmarkPage.isSelected()

            onNodeWithContentDescription(navigateUp).assertDoesNotExist()

            categoryPage.performClick()
            categoryPage.at()
            categoryPage.isSelected()

           onNodeWithContentDescription(navigateUp).assertDoesNotExist()
        }
    }

    @Test
    fun topLevelDestinations_showSearchAndSettings() {
        composeTestRule.apply {
            feedPage.at()
            feedPage.isSelected()

            searchPage.navigationShown()
            settingsPage.navigationShown()

            bookmarkPage.performClick()
            bookmarkPage.at()
            bookmarkPage.isSelected()

            searchPage.navigationShown()
            settingsPage.navigationShown()

            categoryPage.performClick()
            categoryPage.at()
            categoryPage.isSelected()

            searchPage.navigationShown()
            settingsPage.navigationShown()
        }
    }

    @Test
    fun whenSearchIconIsClicked_searchScreenIsShown() {
        composeTestRule.apply {
            feedPage.at()
            feedPage.isSelected()

            onNodeWithContentDescription(navigateUp).assertDoesNotExist()

            searchPage.navigationShown()
            searchPage.performClick()
            searchPage.at()

            onNodeWithContentDescription(navigateUp).assertExists().performClick()

            feedPage.at()
            feedPage.isSelected()
        }
    }

    @Test
    fun whenSettingsIconIsClicked_settingsScreenIsShown() {
        composeTestRule.apply {
            feedPage.at()
            feedPage.isSelected()

            onNodeWithContentDescription(navigateUp).assertDoesNotExist()

            settingsPage.navigationShown()
            settingsPage.performClick()
            settingsPage.at()

            onNodeWithContentDescription(navigateUp).assertExists().performClick()

            feedPage.at()
            feedPage.isSelected()
        }
    }

    @Test(expected = NoActivityResumedException::class)
    fun feedDestination_back_quitsApp() {
        composeTestRule.apply {
            feedPage.at()
            feedPage.isSelected()
            Espresso.pressBack()
        }
    }

    @Test
    fun navigationBar_backFromAnyDestination_returnsToFeed() {
        composeTestRule.apply {
            feedPage.at()
            feedPage.isSelected()

            bookmarkPage.performClick()
            bookmarkPage.isSelected()

            categoryPage.performClick()
            categoryPage.isSelected()

            Espresso.pressBack()

            feedPage.at()
            feedPage.isSelected()
        }
    }
}