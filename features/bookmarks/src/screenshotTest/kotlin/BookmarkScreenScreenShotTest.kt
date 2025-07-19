import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import com.sample.factpedia.core.designsystem.theme.FactPediaGradientBackground
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.features.bookmarks.presentation.state.BookmarkScreenState
import com.sample.factpedia.features.bookmarks.presentation.ui.BookmarksScreen

@PreviewTest
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