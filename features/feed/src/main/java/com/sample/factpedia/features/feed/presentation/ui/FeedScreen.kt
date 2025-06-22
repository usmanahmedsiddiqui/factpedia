package com.sample.factpedia.features.feed.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.designsystem.components.button.FactPediaButton
import com.sample.factpedia.core.designsystem.components.error.FactPediaError
import com.sample.factpedia.core.designsystem.components.loading.FactPediaLoadingBar
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.ui.FactCard
import com.sample.factpedia.core.util.toUiMessageRes
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

                    Spacer(modifier = Modifier.height(Spacings.spacing24))

                    FactPediaButton(
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