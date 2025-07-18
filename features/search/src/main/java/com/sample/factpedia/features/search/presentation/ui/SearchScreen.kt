package com.sample.factpedia.features.search.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.common.utils.toUiMessageRes
import com.sample.factpedia.core.designsystem.components.error.FactPediaErrorText
import com.sample.factpedia.core.designsystem.components.search.FactPediaSearchBar
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.ui.FactCard
import com.sample.factpedia.features.search.R
import com.sample.factpedia.features.search.presentation.action.SearchScreenAction
import com.sample.factpedia.features.search.presentation.state.SearchScreenState
import com.sample.factpedia.features.search.presentation.viewmodel.SearchViewModel

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    SearchScreen(
        state = state,
        onClearSearch = { viewModel.onAction(SearchScreenAction.ClearSearch) },
        onTriggerSearch = { viewModel.onAction(SearchScreenAction.TriggerSearch(it)) },
        onToggleBookmark = { factId, isBookmarked ->
            viewModel.onAction(
                SearchScreenAction.ToggleBookmark(
                    factId, isBookmarked
                )
            )
        }
    )
}

@Composable
internal fun SearchScreen(
    state: SearchScreenState,
    onClearSearch: () -> Unit,
    onTriggerSearch: (String) -> Unit,
    onToggleBookmark: (Int, Boolean) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
    ) {
        FactPediaSearchBar(
            value = state.query,
            placeholder = stringResource(R.string.feature_search_place_holder),
            onValueChange = {
                onTriggerSearch(it)
            },
            onClear = { onClearSearch() }
        )

        Spacer(modifier = Modifier.height(Spacings.spacing16))

        if (state.error != null) {
            FactPediaErrorText(
                text = state.error.toUiMessageRes(LocalContext.current)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.searchResults) { item ->
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
    }
}