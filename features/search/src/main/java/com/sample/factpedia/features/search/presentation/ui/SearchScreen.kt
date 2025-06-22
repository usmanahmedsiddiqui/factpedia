package com.sample.factpedia.features.search.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.designsystem.components.error.FactPediaErrorText
import com.sample.factpedia.core.designsystem.components.search.FactPediaSearchBar
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.ui.FactCard
import com.sample.factpedia.core.util.toUiMessageRes
import com.sample.factpedia.features.search.presentation.action.SearchScreenAction
import com.sample.factpedia.features.search.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Column(
        modifier =
            Modifier
                .fillMaxSize()
    ) {
        FactPediaSearchBar(
            value = state.query,
            placeholder = "Search Facts...",
            onValueChange = {
                viewModel.onAction(SearchScreenAction.TriggerSearch(it))
            },
            onClear = {
                viewModel.onAction(SearchScreenAction.ClearSearch)
            }
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
                        onBookmarkClick = { isBookmarked ->
                            viewModel.onAction(
                                SearchScreenAction.ToggleBookmark(
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