package com.sample.factpedia.features.feed.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.common.utils.toUiMessageRes
import com.sample.factpedia.core.designsystem.components.button.FactPediaButton
import com.sample.factpedia.core.designsystem.components.empty.FactPediaEmptyMessage
import com.sample.factpedia.core.designsystem.components.error.FactPediaError
import com.sample.factpedia.core.designsystem.components.loading.FactPediaLoadingBar
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.theme.FactPediaGradientBackground
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.ui.FactCard
import com.sample.factpedia.features.feed.R
import com.sample.factpedia.features.feed.presentation.actions.FeedScreenAction
import com.sample.factpedia.features.feed.presentation.state.FeedScreenState
import com.sample.factpedia.features.feed.presentation.viewmodel.FeedScreenViewModel

@Composable
fun FactRoute(
    viewModel: FeedScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    FeedScreen(
        state = state,
        onRetryClicked = { viewModel.onAction(FeedScreenAction.Retry) },
        onToggleBookmark = { factId, isBookmarked ->
            viewModel.onAction(
                FeedScreenAction.ToggleBookmark(
                    factId,
                    isBookmarked
                )
            )
        },
        onNextFact = {
            viewModel.onAction(FeedScreenAction.Refresh)
        }
    )
}

@Composable
internal fun FeedScreen(
    state: FeedScreenState,
    onRetryClicked: () -> Unit,
    onToggleBookmark: (Int, Boolean) -> Unit,
    onNextFact: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacings.spacing16),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> {
                val loadingDescription = stringResource(R.string.feature_feed_loading)

                FactPediaLoadingBar(
                    Modifier.semantics { contentDescription = loadingDescription }
                )
            }

            state.error != null -> {
                FactPediaError(
                    text = state.error.toUiMessageRes(LocalContext.current),
                    onRetry = { onRetryClicked() }
                )
            }

            state.fact != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FactPediaText(
                        stringResource(R.string.feature_feed_did_you_know),
                        textStyle = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(Spacings.spacing16))

                    FactPediaText(
                        stringResource(R.string.feature_feed_explore_facts),
                        textStyle = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(Spacings.spacing32))

                    FactCard(
                        fact = state.fact,
                        onBookmarkClick = { isBookmarked ->
                            onToggleBookmark(state.fact.id, isBookmarked)
                        },
                        onShareClick = {}
                    )

                    Spacer(modifier = Modifier.height(Spacings.spacing32))

                    FactPediaButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.feature_feed_next_fact),
                        onClick = {
                            onNextFact()
                        }
                    )
                }
            }

            else -> {
                FactPediaEmptyMessage(
                    text = stringResource(R.string.feature_feed_no_fact_found)
                )
            }
        }
    }
}

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun FeedScreenPreview() {
    FactPediaTheme {
        val isDark = isSystemInDarkTheme()
        FactPediaGradientBackground(isDark = isDark) {
            FeedScreen(
                state = FeedScreenState(
                    isLoading = false,
                    error = null,
                    fact =  BookmarkedFact(
                        id = 1,
                        fact = "Cats sleep for 70% of their lives.",
                        categoryName = "Animals",
                        categoryId = 10,
                        isBookmarked = true
                    )
                ),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> },
                onNextFact = {}
            )
        }
    }
}