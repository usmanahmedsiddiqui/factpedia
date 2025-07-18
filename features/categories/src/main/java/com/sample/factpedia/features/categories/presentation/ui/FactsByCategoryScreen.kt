package com.sample.factpedia.features.categories.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.common.utils.toUiMessageRes
import com.sample.factpedia.core.designsystem.components.empty.FactPediaEmptyMessage
import com.sample.factpedia.core.designsystem.components.error.FactPediaError
import com.sample.factpedia.core.designsystem.components.loading.FactPediaLoadingBar
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.ui.FactCard
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
                    modifier = Modifier.padding(Spacings.spacing16)
                )

                LazyColumn {
                    items(state.facts) { item ->
                        FactCard(
                            fact = item,
                            modifier = Modifier.padding(
                                horizontal = Spacings.spacing16,
                                vertical = Spacings.spacing8
                            ),
                            onBookmarkClick = { isBookmarked ->
                                onToggleBookmark(item.id, isBookmarked)
                            },
                            onShareClick = {}
                        )
                    }
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