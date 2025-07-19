package com.sample.factpedia.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
inline fun <T> PreviewAwareLazyColumn(
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
    items: List<T>,
    crossinline itemContent: @Composable (T) -> Unit
) {
    if (isPreview) {
        Column(modifier = modifier) {
            items.forEach { item ->
                itemContent(item)
            }
        }
    } else {
        LazyColumn(modifier = modifier) {
            items(items) { item ->
                itemContent(item)
            }
        }
    }
}