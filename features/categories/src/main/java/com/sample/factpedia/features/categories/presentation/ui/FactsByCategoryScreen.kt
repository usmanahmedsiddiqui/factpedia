package com.sample.factpedia.features.categories.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.ui.FactCard
import com.sample.factpedia.core.ui.PreviewAwareLazyColumn
import com.sample.factpedia.features.categories.R
import com.sample.factpedia.features.categories.presentation.actions.FactsByCategoryScreenAction
import com.sample.factpedia.features.categories.presentation.state.FactsByCategoryScreenState
import com.sample.factpedia.features.categories.presentation.viewmodel.FactsByCategoryViewModel

@Composable
fun FactsByCategoryRoute(
    categoryId: Int,
    categoryName: String,
    viewModel: FactsByCategoryViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    FactsByCategoryScreen(
        categoryName = categoryName,
        state = state,
        onRetryClicked = {
            viewModel.onAction(
                FactsByCategoryScreenAction.RetryClicked(
                    categoryId
                )
            )
        },
        onToggleBookmark = { factId, isBookmarked ->
            viewModel.onAction(
                FactsByCategoryScreenAction.ToggleBookmark(
                    factId, isBookmarked
                )
            )
        }
    )
}

@Composable
internal fun FactsByCategoryScreen(
    isPreview: Boolean = false,
    categoryName: String,
    state: FactsByCategoryScreenState,
    onRetryClicked: () -> Unit,
    onToggleBookmark: (Int, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            state.isLoading -> {
                val loadingDescription = stringResource(R.string.feature_facts_category_loading)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FactPediaLoadingBar(
                        Modifier.semantics { contentDescription = loadingDescription }
                    )
                }

            }

            state.facts.isNotEmpty() -> {
                FactPediaText(
                    text = categoryName,
                    textStyle = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .testTag("title_$categoryName")
                        .padding(Spacings.spacing16)
                )

                PreviewAwareLazyColumn(
                    isPreview = isPreview,
                    items = state.facts
                ) { fact ->
                    FactCard(
                        fact = fact,
                        modifier = Modifier.padding(
                            horizontal = Spacings.spacing16,
                            vertical = Spacings.spacing8
                        ),
                        onBookmarkClick = { isBookmarked ->
                            onToggleBookmark(fact.id, isBookmarked)
                        },
                        onShareClick = {}
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
                        modifier = Modifier.align(Alignment.Center),
                        onRetry = {
                            onRetryClicked()
                        }
                    )
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FactPediaEmptyMessage(
                        text = stringResource(R.string.feature_no_facts_found_in_category)
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
fun FactsByCategoryScreenPreview() {
    FactPediaTheme {
        val isDark = isSystemInDarkTheme()
        FactPediaGradientBackground(isDark = isDark) {
            FactsByCategoryScreen(
                isPreview = true,
                categoryName = "Animals",
                state = FactsByCategoryScreenState(
                  isLoading = false,
                    error = null,
                    facts = listOf(
                        BookmarkedFact(
                            id = 1,
                            fact = "Cats sleep for 70% of their lives.",
                            categoryName = "Animals",
                            categoryId = 10,
                            isBookmarked = true
                        ),
                        BookmarkedFact(
                            id = 2,
                            fact = "A giraffe's tongue is blue and can be 20 inches long.",
                            categoryName = "Animals",
                            categoryId = 11,
                            isBookmarked = true
                        )
                    )
                ),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> }

            )
        }
    }
}