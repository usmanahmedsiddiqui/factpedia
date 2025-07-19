package com.sample.factpedia.core.designsystem.components.error

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.sample.factpedia.core.designsystem.components.button.FactPediaButton
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.core.designsystem.theme.Spacings

@Composable
fun FactPediaError(
    text: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = Spacings.spacing24)
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FactPediaErrorText(text = text)

        FactPediaButton(
            modifier = Modifier.testTag("retry_button").fillMaxWidth(),
            text = "Retry",
            onClick = onRetry
        )
    }
}

@Composable
fun FactPediaErrorText(text: String) {
    FactPediaText(
        text = text,
        textStyle = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(bottom = Spacings.spacing16),
    )
}

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