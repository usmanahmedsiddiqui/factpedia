package com.sample.factpedia.features.bookmarks.presentation.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.designsystem.components.empty.FactPediaEmptyMessage
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.ui.FactCard
import com.sample.factpedia.features.bookmarks.R
import com.sample.factpedia.features.bookmarks.presentation.action.BookmarkScreenAction
import com.sample.factpedia.features.bookmarks.presentation.state.BookmarkScreenState
import com.sample.factpedia.features.bookmarks.presentation.viewmodel.BookmarkViewModel

@Composable
fun BookmarksRoute(
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    BookmarksScreen(
        state = state,
        onBookmarkRemoved = { bookmarkId ->
            viewModel.onAction(
                BookmarkScreenAction.RemoveBookmark(bookmarkId)
            )
        }
    )
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun BookmarksScreen(
    state: BookmarkScreenState,
    onBookmarkRemoved: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            state.facts.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FactPediaEmptyMessage(
                        text =  stringResource(R.string.feature_bookmark_not_found),
                    )
                }
            }

            else -> {
                LazyColumn {
                    items(state.facts) { item ->
                        FactCard(
                            fact = item,
                            modifier = Modifier.padding(
                                horizontal = Spacings.spacing16,
                                vertical = Spacings.spacing8
                            ),
                            onBookmarkClick = {
                                onBookmarkRemoved(item.id)
                            },
                            onShareClick = {}
                        )
                    }
                }
            }
        }
    }
}