package com.sample.factpedia.features.bookmarks.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.designsystem.components.empty.FactPediaEmptyMessage
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.ui.FactCard
import com.sample.factpedia.features.bookmarks.presentation.action.BookmarkScreenAction
import com.sample.factpedia.features.bookmarks.presentation.viewmodel.BookmarkViewModel

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

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
                        text = "No bookmarks found!",
                    )
                }
            }

            else -> {
                LazyColumn {
                    items(state.facts) { item ->
                        FactCard(
                            fact = item,
                            modifier = Modifier.padding(horizontal = Spacings.spacing16, vertical = Spacings.spacing8),
                            onBookmarkClick = { isBookmarked ->
                                viewModel.onAction(
                                    BookmarkScreenAction.ToggleBookmark(
                                        item.id,
                                        isBookmarked
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