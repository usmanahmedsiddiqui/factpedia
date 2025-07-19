package com.sample.factpedia.core.designsystem.components.button

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freenow.driver.design.compose.essentials.CornerRadius
import com.sample.factpedia.core.designsystem.theme.FactPediaGradientBackground
import com.sample.factpedia.core.designsystem.theme.FactPediaTheme
import com.sample.factpedia.core.designsystem.theme.Spacings

@Composable
fun FactPediaButton(
    text: String,
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyles.PrimaryInverted,
    buttonSize: ButtonSize = ButtonSizes.Large,
    shape: RoundedCornerShape = RoundedCornerShape(CornerRadius.corner32),
    onClick: () -> Unit = {},
    @DrawableRes iconRes: Int? = null,
) {
    Button(
        onClick = { onClick() },
        colors = buttonStyle.colors(),
        shape = shape,
        contentPadding =
            PaddingValues(
                start = Spacings.spacing16,
                end = Spacings.spacing16,
            ),
        modifier =
            Modifier
                .defaultMinSize(minHeight = buttonSize.minHeight())
                .then(modifier),
    ) {

        iconRes?.let {
            Image(
                modifier =
                    Modifier
                        .padding(3.dp)
                        .width(buttonSize.iconSize())
                        .height(buttonSize.iconSize()),
                contentDescription = null,
                painter = painterResource(id = it),
                colorFilter =
                    ColorFilter.tint(
                        color = buttonStyle.colors().contentColor,
                    ),
            )
            Spacer(
                modifier = Modifier.width(Spacings.spacing8),
            )
        }

        Text(
            text = text,
            style =
                buttonSize.textStyle().copy(
                    color = buttonStyle.colors().contentColor,
                ),
            modifier = Modifier.animateContentSize(),
        )
    }
}

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun FactPediaButtonPreview_Large() {
    FactPediaTheme {
        FactPediaButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Large",
            buttonStyle = ButtonStyles.PrimaryInverted,
            buttonSize = ButtonSizes.Large,
            onClick = {}
        )
    }
}

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun FactPediaButtonPreview_Medium() {
    FactPediaTheme {
        FactPediaButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Medium",
            buttonStyle = ButtonStyles.PrimaryInverted,
            buttonSize = ButtonSizes.Medium,
            onClick = {}
        )
    }
}

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun FactPediaButtonPreview_Small() {
    FactPediaTheme {
        FactPediaButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Small",
            buttonStyle = ButtonStyles.PrimaryInverted,
            buttonSize = ButtonSizes.Small,
            onClick = {}
        )
    }
}

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun FactPediaButtonPreview_WithIcon() {
    FactPediaTheme {
        FactPediaButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Button with Icon",
            buttonStyle = ButtonStyles.PrimaryInverted,
            buttonSize = ButtonSizes.Medium,
            iconRes = android.R.drawable.ic_media_play,
            onClick = {}
        )
    }
}