import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import com.sample.factpedia.core.designsystem.theme.FactPediaGradientBackground
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.core.model.domain.ThemePreference
import com.sample.factpedia.features.settings.presentation.ui.SettingsScreen

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
            SettingsScreen(
                currentTheme = if (isDark) ThemePreference.DARK else ThemePreference.LIGHT,
                onThemeChanged = {}
            )
        }
    }
}