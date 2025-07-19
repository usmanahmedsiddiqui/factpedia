import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import com.sample.factpedia.core.designsystem.components.error.FactPediaError
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme

@PreviewTest
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun FactPediaErrorStatePreview() {
    FactPediaTheme {
        FactPediaError(
            text = "There was an error loading data from server",
            onRetry = {}
        )
    }
}