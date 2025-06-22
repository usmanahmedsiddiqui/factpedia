package com.sample.factpedia.features.categories.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.designsystem.components.error.FactPediaError
import com.sample.factpedia.core.designsystem.components.loading.FactPediaLoadingBar
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.ui.FactCard
import com.sample.factpedia.core.util.toUiMessageRes
import com.sample.factpedia.features.categories.presentation.actions.FactsByCategoryScreenAction
import com.sample.factpedia.features.categories.presentation.viewmodel.FactsByCategoryViewModel

@Composable
fun FactsByCategoryScreen(
    categoryId: Int,
    categoryName: String,
    viewModel: FactsByCategoryViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FactPediaLoadingBar()
                }

            }

            state.error != null && state.facts.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FactPediaError(
                        text = state.error.toUiMessageRes(LocalContext.current),
                        modifier = Modifier.align(Alignment.Center),
                        onRetry = {
                            viewModel.onAction(
                                FactsByCategoryScreenAction.RetryClicked(
                                    categoryId
                                )
                            )
                        }
                    )
                }
            }

            state.facts.isNotEmpty() -> {
                FactPediaText(
                    text = categoryName,
                    textStyle = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(Spacings.spacing16)
                )

                LazyColumn {
                    items(state.facts) { item ->
                        FactCard(
                            fact = item,
                            onBookmarkClick = { isBookmarked ->
                                viewModel.onAction(
                                    FactsByCategoryScreenAction.ToggleBookmark(
                                        item.id, isBookmarked
                                    )
                                )
                            },
                            onShareClick = {}
                        )
                    }
                }
            }
        }
    }
}