package com.sample.factpedia.core.designsystem.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun FactPediaText(
    text: String,
    textStyle: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    maxLines:Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = textStyle,
        color = color,
        modifier = modifier,
        maxLines = maxLines,
    )

}