import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest
import com.sample.factpedia.core.designsystem.components.button.ButtonSizes
import com.sample.factpedia.core.designsystem.components.button.ButtonStyles
import com.sample.factpedia.core.designsystem.components.button.FactPediaButton
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme

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
fun FactPediaButtonPreview_Large() {
    FactPediaTheme {
        FactPediaButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Large",
            buttonStyle = ButtonStyles.PrimaryInverted,
            buttonSize = ButtonSizes.Large,
            onClick = {}
        )
    }
}

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
fun FactPediaButtonPreview_Medium() {
    FactPediaTheme {
        FactPediaButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Medium",
            buttonStyle = ButtonStyles.PrimaryInverted,
            buttonSize = ButtonSizes.Medium,
            onClick = {}
        )
    }
}

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
fun FactPediaButtonPreview_Small() {
    FactPediaTheme {
        FactPediaButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Small",
            buttonStyle = ButtonStyles.PrimaryInverted,
            buttonSize = ButtonSizes.Small,
            onClick = {}
        )
    }
}

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
fun FactPediaButtonPreview_WithIcon() {
    FactPediaTheme {
        FactPediaButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Button with Icon",
            buttonStyle = ButtonStyles.PrimaryInverted,
            buttonSize = ButtonSizes.Medium,
            iconRes = android.R.drawable.ic_media_play,
            onClick = {}
        )
    }
}