package com.sample.factpedia.core.designsystem.components.error

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.sample.factpedia.core.designsystem.components.button.FactPediaButton
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.theme.Spacings

@Composable
fun FactPediaError(
    text: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Spacings.spacing24)
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FactPediaErrorText(text = text)

        FactPediaButton(
            modifier = Modifier.testTag("retry_button"),
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