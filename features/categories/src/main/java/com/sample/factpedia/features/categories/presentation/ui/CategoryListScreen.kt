package com.sample.factpedia.features.categories.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
                LazyColumn {
                    items(state.categories) { category ->
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