package com.sample.factpedia.core.designsystem.components.loading

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FactPediaLoadingBar(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(modifier = modifier)
}