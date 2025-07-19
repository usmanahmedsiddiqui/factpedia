import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import com.sample.factpedia.core.designsystem.theme.FactPediaGradientBackground
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.features.categories.presentation.state.FactsByCategoryScreenState
import com.sample.factpedia.features.categories.presentation.ui.FactsByCategoryScreen

@PreviewTest
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun FactsByCategoryScreenPreview() {
    FactPediaTheme {
        val isDark = isSystemInDarkTheme()
        FactPediaGradientBackground(isDark = isDark) {
            FactsByCategoryScreen(
                isPreview = true,
                categoryName = "Animals",
                state = FactsByCategoryScreenState(
                    isLoading = false,
                    error = null,
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
                            fact = "A giraffe's tongue is blue and can be 20 inches long.",
                            categoryName = "Animals",
                            categoryId = 11,
                            isBookmarked = true
                        )
                    )
                ),
                onRetryClicked = {},
                onToggleBookmark = { _, _ -> }

            )
        }
    }
}