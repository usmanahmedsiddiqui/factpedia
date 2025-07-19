package com.sample.factpedia.core.designsystem.components.empty

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme

@Composable
fun FactPediaEmptyMessage(
    text: String
) {
    FactPediaText(
        text = text,
        textStyle = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimary,
    )
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun FactPediaEmptyStatePreview() {
    FactPediaTheme {
        FactPediaEmptyMessage(
            text = "No Facts Found",
        )
    }
}