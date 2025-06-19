package com.sample.factpedia.features.bookmarks.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.features.bookmarks.presentation.action.BookmarkScreenAction
import com.sample.factpedia.features.bookmarks.presentation.viewmodel.BookmarkViewModel

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    when {
        state.facts.isEmpty() -> {
            Text("No bookmarks!")
        }

        else -> {
            LazyColumn {
                items(state.facts) { item ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = item.fact, modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            viewModel.onAction(BookmarkScreenAction.ToggleBookmark(item.id, !item.isBookmarked))
                        }) {
                            Icon(
                                imageVector = if (item.isBookmarked) Icons.Default.Bookmarks else Icons.Default.BookmarkBorder,
                                contentDescription = if (item.isBookmarked) "Remove bookmark" else "Add bookmark"
                            )
                        }
                    }
                }
            }
        }
    }
}