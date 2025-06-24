package com.sample.factpedia.features.feed.presentation.ui

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
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.common.utils.toUiMessageRes
import com.sample.factpedia.core.designsystem.components.button.FactPediaButton
import com.sample.factpedia.core.designsystem.components.error.FactPediaError
import com.sample.factpedia.core.designsystem.components.loading.FactPediaLoadingBar
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.ui.FactCard
import com.sample.factpedia.features.feed.presentation.actions.FeedScreenAction
import com.sample.factpedia.features.feed.presentation.viewmodel.FeedScreenViewModel

@Composable
fun FeedScreen(
    viewModel: FeedScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacings.spacing16),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading && state.fact == null -> {
                FactPediaLoadingBar()
            }

            state.error != null && state.fact == null -> {
                FactPediaError(
                    text = state.error.toUiMessageRes(LocalContext.current),
                    onRetry = { viewModel.onAction(FeedScreenAction.Retry) }
                )
            }

            state.fact != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    FactPediaText(
                        "Did You Know?",
                        textStyle = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(Spacings.spacing16))

                    FactPediaText(
                        "Explore interesting facts curated for your curiosity.",
                        textStyle = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(Spacings.spacing32))

                    FactCard(
                        fact = state.fact,
                        onBookmarkClick = { isBookmarked ->
                            viewModel.onAction(
                                FeedScreenAction.ToggleBookmark(
                                    state.fact.id,
                                    isBookmarked
                                )
                            )
                        },
                        onShareClick = {
                            viewModel.onAction(FeedScreenAction.ShareFact(state.fact))
                        }
                    )

                    Spacer(modifier = Modifier.height(Spacings.spacing32))

                    FactPediaButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Next Fact",
                        onClick = {
                            viewModel.onAction(FeedScreenAction.Refresh)
                        }
                    )
                }
            }
        }
    }
}