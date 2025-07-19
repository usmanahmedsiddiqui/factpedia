import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import com.sample.factpedia.core.designsystem.theme.FactPediaGradientBackground
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.features.categories.domain.model.Category
import com.sample.factpedia.features.categories.presentation.state.CategoryScreenState
import com.sample.factpedia.features.categories.presentation.ui.CategoryListScreen

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
fun CategoryScreenPreview() {
    FactPediaTheme {
        val isDark = isSystemInDarkTheme()
        FactPediaGradientBackground(isDark = isDark) {
            CategoryListScreen(
                inPreview = true,
                state = CategoryScreenState(
                    isLoading = false,
                    error = null,
                    categories = listOf(
                        Category(
                            id = 1,
                            name = "Animals",
                        ),
                        Category(
                            id = 1,
                            name = "General",
                        ),
                        Category(
                            id = 1,
                            name = "Celebrity",
                        ),
                    )
                ),

                onCategoryClick = {},
                onRetryClicked = {},
            )
        }
    }
}