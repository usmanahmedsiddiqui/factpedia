package com.sample.factpedia.features.search.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.util.toUiMessageRes
import com.sample.factpedia.features.search.presentation.action.SearchScreenAction
import com.sample.factpedia.features.search.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { newQuery ->
                viewModel.onAction(SearchScreenAction.TriggerSearch(newQuery))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search facts...") },
            trailingIcon = {
                if (state.query.isNotEmpty()) {
                    IconButton(onClick = {
                        viewModel.onAction(SearchScreenAction.ClearSearch)
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.error != null) {
            Text(
                text = state.error.toUiMessageRes(LocalContext.current),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.searchResults) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.fact,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        )

                        IconButton(onClick = {
                            viewModel.onAction(SearchScreenAction.ToggleBookmark(item.id, !item.isBookmarked))
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