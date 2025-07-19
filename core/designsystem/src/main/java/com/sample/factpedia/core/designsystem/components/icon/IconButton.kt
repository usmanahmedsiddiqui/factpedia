package com.sample.factpedia.core.designsystem.components.icon

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme

@Composable
fun FactPediaIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String = "",
    style: IconButtonStyle = IconButtonStyles.Primary,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            icon,
            contentDescription = contentDescription,
            tint = style.tintColor()
        )
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FactPediaIconButtonPreview() {
    FactPediaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            FactPediaIconButton(
                icon = Icons.Default.Favorite,
                contentDescription = "Favorite",
                onClick = {}
            )
        }
    }
}