package com.sample.factpedia.core.designsystem.components.button

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

object ButtonStyles {
    val PrimaryInverted =
        object : ButtonStyle {
            @Composable
            override fun colors(): ButtonColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
                disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
            )
        }
}

@Stable
interface ButtonStyle {
    @Composable
    fun colors(): ButtonColors
}
