package com.sample.factpedia.features.bookmarks.presentation.ui

import android.content.res.Configuration
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sample.factpedia.core.designsystem.components.empty.FactPediaEmptyMessage
import com.sample.factpedia.core.designsystem.theme.FactPediaGradientBackground
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.ui.FactCard
import com.sample.factpedia.core.ui.PreviewAwareLazyColumn
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
    isPreview: Boolean = false,
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
                        text = stringResource(R.string.feature_bookmark_not_found),
                    )
                }
            }

            else -> {

                PreviewAwareLazyColumn(
                    isPreview = isPreview,
                    items = state.facts
                ) { fact ->
                    FactCard(
                        fact = fact,
                        modifier = Modifier.padding(
                            horizontal = Spacings.spacing16,
                            vertical = Spacings.spacing8
                        ),
                        onBookmarkClick = {
                            onBookmarkRemoved(fact.id)
                        },
                        onShareClick = {}
                    )
                }
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
            BookmarksScreen(
                isPreview = true,
                state = BookmarkScreenState(
                    facts = listOf(
                        BookmarkedFact(
                            id = 1,
                            fact = "Cats sleep for 70% of their lives.",
                            categoryName = "Animals",
                            categoryId = 10,
                            isBookmarked = true
                        ),
                        BookmarkedFact(
                            id = 2,
                            fact = "Bananas are berries, but strawberries aren't.",
                            categoryName = "Food",
                            categoryId = 11,
                            isBookmarked = true
                        )
                    )
                ),
                onBookmarkRemoved = {}
            )
        }
    }
}