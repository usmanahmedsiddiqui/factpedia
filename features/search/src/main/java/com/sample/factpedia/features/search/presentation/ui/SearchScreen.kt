package com.sample.factpedia.features.search.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.common.utils.toUiMessageRes
import com.sample.factpedia.core.designsystem.components.error.FactPediaErrorText
import com.sample.factpedia.core.designsystem.components.search.FactPediaSearchBar
import com.sample.factpedia.core.designsystem.theme.FactPediaGradientBackground
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.ui.FactCard
import com.sample.factpedia.core.ui.PreviewAwareLazyColumn
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
    isPreview: Boolean = false,
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
            PreviewAwareLazyColumn(
                isPreview = isPreview,
                items = state.searchResults
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
fun BookmarksScreenPreview() {
    FactPediaTheme {
        val isDark = isSystemInDarkTheme()
        FactPediaGradientBackground(isDark = isDark) {
           SearchScreen(
               isPreview = true,
               state = SearchScreenState(
                   query = "animal",
                   error = null,
                   searchResults = listOf(
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
                           isBookmarked = false
                       )
                   )
               ),
               onClearSearch = {},
               onTriggerSearch = {},
               onToggleBookmark = { _, _ -> }
           )
        }
    }
}