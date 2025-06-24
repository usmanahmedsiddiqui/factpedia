package com.sample.factpedia.core.designsystem.components.tag

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.freenow.driver.design.compose.essentials.CornerRadius
import com.sample.factpedia.core.designsystem.theme.Spacings


@Composable
fun Tag(
    text: String,
    modifier: Modifier = Modifier,
    tagStyle: TagStyle = TagStyles.Primary,
    @DrawableRes imageResourceId: Int? = null,
    isUpperCase: Boolean = true,
    isTruncate: Boolean = false,
    maxLines: Int? = null,
) {
    Row(
        modifier =
            modifier
                .background(
                    color = tagStyle.backgroundColor(),
                    shape = RoundedCornerShape(CornerRadius.corner8),
                )
                .padding(
                    horizontal = Spacings.spacing12,
                    vertical = Spacings.spacing8,
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        imageResourceId?.let {
            Image(
                painter = painterResource(id = imageResourceId),
                contentDescription = null,
                modifier =
                    Modifier
                        .width(Spacings.spacing16)
                        .height(Spacings.spacing16),
                colorFilter = tagStyle.imageResourceColorFilter(),
            )
            Spacer(
                modifier =
                    Modifier.width(
                        Spacings.spacing8,
                    ),
            )
        }
        if (isTruncate && maxLines != null) {
            Text(
                text = if (isUpperCase) text.uppercase() else text,
                style = tagStyle.textStyle(),
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            Text(
                text = if (isUpperCase) text.uppercase() else text,
                style = tagStyle.textStyle(),
            )
        }
    }
}
