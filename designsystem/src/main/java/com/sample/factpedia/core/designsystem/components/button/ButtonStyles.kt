package com.sample.factpedia.core.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

object ButtonStyles {
    val Primary =
        object : ButtonStyle {
            @Composable
            override fun colors(): ButtonColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
            )
        }
}

@Stable
interface ButtonStyle {
    @Composable
    fun colors(): ButtonColors
}
