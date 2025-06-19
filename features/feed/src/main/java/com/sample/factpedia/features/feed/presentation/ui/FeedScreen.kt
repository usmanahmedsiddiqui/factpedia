package com.sample.factpedia.features.feed.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.features.feed.presentation.actions.FeedScreenAction
import com.sample.factpedia.features.feed.presentation.viewmodel.FeedScreenViewModel

@Composable
fun FeedScreen(
    viewModel: FeedScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading && state.fact == null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.error != null && state.fact == null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Something went wrong")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        viewModel.onAction(FeedScreenAction.Retry)
                    }) {
                        Text("Retry")
                    }
                }
            }

            state.fact != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = state.fact.fact,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { viewModel.onAction(FeedScreenAction.ToggleBookmark(state.fact.id, !state.fact.isBookmarked)) }) {
                            Icon(
                                imageVector = if (state.fact.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "Bookmark"
                            )
                        }

                        IconButton(onClick = { viewModel.onAction(FeedScreenAction.ShareFact(state.fact)) }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share"
                            )
                        }

                        Button(onClick = { viewModel.onAction(FeedScreenAction.Refresh) }) {
                            Text("Refresh")
                        }
                    }
                }
            }
        }
    }
}