package com.sample.factpedia.features.categories.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.common.utils.toUiMessageRes
import com.sample.factpedia.core.designsystem.components.empty.FactPediaEmptyMessage
import com.sample.factpedia.core.designsystem.components.error.FactPediaError
import com.sample.factpedia.core.designsystem.components.loading.FactPediaLoadingBar
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.theme.FactPediaGradientBackground
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.ui.PreviewAwareLazyColumn
import com.sample.factpedia.features.categories.R
import com.sample.factpedia.features.categories.domain.model.Category
import com.sample.factpedia.features.categories.presentation.actions.CategoryScreenAction
import com.sample.factpedia.features.categories.presentation.state.CategoryScreenState
import com.sample.factpedia.features.categories.presentation.viewmodel.CategoriesViewModel

@Composable
fun CategoryListRoute(
    viewModel: CategoriesViewModel = hiltViewModel(),
    onCategoryClick: (Category) -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    CategoryListScreen(
        state = state,
        onCategoryClick = onCategoryClick,
        onRetryClicked = { viewModel.onAction(CategoryScreenAction.RetryClicked) },
    )
}

@Composable
internal fun CategoryListScreen(
    state: CategoryScreenState,
    onCategoryClick: (Category) -> Unit,
    onRetryClicked: () -> Unit,
    inPreview: Boolean = false,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            state.isLoading -> {
                val loadingDescription = stringResource(R.string.feature_categories_loading)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FactPediaLoadingBar(
                        Modifier.semantics { contentDescription = loadingDescription }
                    )
                }
            }

            state.categories.isNotEmpty() -> {
                PreviewAwareLazyColumn(
                    isPreview = inPreview,
                    items = state.categories,
                    modifier = Modifier.testTag("categories:list").semantics { contentDescription = "categories:list" }
                ) { category ->
                    FactPediaText(
                        text = category.name,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCategoryClick(category) }
                            .padding(Spacings.spacing16)
                    )
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FactPediaError(
                        text = state.error.toUiMessageRes(LocalContext.current),
                        onRetry = { onRetryClicked() }
                    )
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FactPediaEmptyMessage(
                        text = stringResource(R.string.feature_no_categories_found)
                    )
                }
            }
        }
    }
}


@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun CategoryScreenPreview() {
    FactPediaTheme {
        val isDark = isSystemInDarkTheme()
        FactPediaGradientBackground(isDark = isDark) {
            CategoryListScreen(
                inPreview = true,
                state = CategoryScreenState(
                    isLoading = false,
                    error = null,
                    categories = listOf(
                        Category(
                            id = 1,
                            name = "Animals",
                        ),
                        Category(
                            id = 1,
                            name = "General",
                        ),
                        Category(
                            id = 1,
                            name = "Celebrity",
                        ),
                    )
                ),
                onCategoryClick = {},
                onRetryClicked = {},
            )
        }
    }
}