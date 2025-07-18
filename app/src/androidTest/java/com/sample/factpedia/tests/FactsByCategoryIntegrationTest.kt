package com.sample.factpedia.tests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.sample.factpedia.MainActivity
import com.sample.factpedia.core.network.di.NetworkModule
import com.sample.factpedia.pages.CategoryPage
import com.sample.factpedia.pages.FactByCategoryPage
import com.sample.factpedia.pages.FeedPage
import com.sample.factpedia.util.dispatchMockResponses
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
class FactsByCategoryIntegrationTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var mockWebServer: MockWebServer

    private lateinit var feedPage: FeedPage
    private lateinit var categoryPage: CategoryPage
    private lateinit var factByCategoryPage: FactByCategoryPage

    @Before
    fun setup() {
        hiltRule.inject()
        feedPage = FeedPage(composeTestRule.activity, composeTestRule)
        categoryPage = CategoryPage(composeTestRule.activity, composeTestRule)
        factByCategoryPage = FactByCategoryPage(composeTestRule)

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
    fun categoryScreen_showCorrectData() {
        composeTestRule.apply {
            feedPage.at()
            feedPage.isSelected()

            categoryPage.performClick()
            categoryPage.at()
            categoryPage.isSelected()

            categoryPage.areCategoriesShown(listOf("General", "Animals", "Celebrity"))
            categoryPage.clickCategory("Celebrity")

            factByCategoryPage.at("title_Celebrity", listOf("Fact 1", "Fact 2"))
        }
    }
}