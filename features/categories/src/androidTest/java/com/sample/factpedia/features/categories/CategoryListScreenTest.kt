package com.sample.factpedia.features.categories

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.features.categories.domain.model.Category
import com.sample.factpedia.features.categories.presentation.state.CategoryScreenState
import com.sample.factpedia.features.categories.presentation.ui.CategoryListScreen
import org.junit.Rule
import org.junit.Test
import com.sample.factpedia.core.common.R as Ln10R

class CategoryListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showsLoadingSpinner() {
        composeTestRule.setContent {
            CategoryListScreen(
                state = CategoryScreenState(isLoading = true),
                onRetryClicked = {},
                onCategoryClick = {},
            )
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(R.string.feature_categories_loading),
            )
            .assertExists()
    }

    @Test
    fun whenHasNoCategories_showsEmptyState() {
        composeTestRule.setContent {
            CategoryListScreen(
                state = CategoryScreenState(emptyList()),
                onRetryClicked = {},
                onCategoryClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.feature_no_categories_found),
            )
            .assertExists()
    }

    @Test
    fun whenHasError_showsErrorState() {
        composeTestRule.setContent {
            CategoryListScreen(
                state = CategoryScreenState(error = DataError.Network.TIMEOUT),
                onRetryClicked = {},
                onCategoryClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(Ln10R.string.error_timeout),
            )
            .assertExists()
    }


    @Test
    fun whenHasCategories_showsCategories() {
        val categories =  listOf(
            Category(1, "Category 1"),
            Category(2, "Category 2"),
        )
        composeTestRule.setContent {
            CategoryListScreen(
                state = CategoryScreenState(
                    categories
                ),
                onRetryClicked = {},
                onCategoryClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                categories[0].name,
            )
            .assertExists()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    categories[1].name,
                ),
            )
    }

    @Test
    fun whenHasError_clickRetry_callsOnRetryClicked() {
        var retryClicked = false

        composeTestRule.setContent {
            CategoryListScreen(
                state = CategoryScreenState(error = DataError.Network.TIMEOUT),
                onRetryClicked = { retryClicked = true },
                onCategoryClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag("retry_button")
            .performClick()

        assert(retryClicked)
    }

    @Test
    fun whenCategoryClicked_callsOnCategoryClick() {
        val clickedCategories = mutableListOf<Category>()
        val categories = listOf(
            Category(1, "Category 1"),
            Category(2, "Category 2")
        )

        composeTestRule.setContent {
            CategoryListScreen(
                state = CategoryScreenState(categories = categories),
                onRetryClicked = {},
                onCategoryClick = { clickedCategories.add(it) }
            )
        }

        composeTestRule
            .onNodeWithText("Category 1")
            .performClick()

        assert(clickedCategories.size == 1)
        assert(clickedCategories[0] == categories[0])
    }
}