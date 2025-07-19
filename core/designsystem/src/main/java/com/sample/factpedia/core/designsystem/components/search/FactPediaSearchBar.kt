package com.sample.factpedia.core.designsystem.components.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sample.factpedia.core.designsystem.components.icon.FactPediaIconButton
import com.sample.factpedia.core.designsystem.components.text.FactPediaText
import com.sample.factpedia.core.designsystem.icons.FactPediaIcons
import com.sample.factpedia.core.designsystem.theme.Spacings

@Composable
fun FactPediaSearchBar(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth()
            .padding(Spacings.spacing16),
        placeholder = {
            FactPediaText(
                text = placeholder,
                textStyle = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                FactPediaIconButton(
                    icon = FactPediaIcons.Close,
                    contentDescription = "Clear",
                    onClick = onClear
                )
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = RoundedCornerShape(Spacings.spacing16)
    )
}