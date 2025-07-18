package com.sample.factpedia.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import com.sample.factpedia.core.designsystem.components.icon.FactPediaIconButton
import com.sample.factpedia.core.designsystem.components.tag.Tag
import com.sample.factpedia.core.designsystem.icons.FactPediaIcons
import com.sample.factpedia.core.designsystem.theme.Spacings
import com.sample.factpedia.core.model.domain.BookmarkedFact

@Composable
fun FactCard(
    fact: BookmarkedFact,
    onBookmarkClick: (Boolean) -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(Spacings.spacing4, RoundedCornerShape(Spacings.spacing16)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
    ) {
        Column(Modifier.padding(Spacings.spacing16)) {
            Text(
                text = fact.fact,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(Spacings.spacing8))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tag(
                    text = fact.categoryName
                )
                Row {
                    FactPediaIconButton(
                        icon = FactPediaIcons.Share,
                        contentDescription = "Share",
                        onClick = onShareClick
                    )

                    FactPediaIconButton(
                        modifier = if (fact.isBookmarked) Modifier.testTag("bookmark_${fact.id}") else Modifier.testTag(
                            "unbookmark_${fact.id}"
                        ),
                        icon = if (fact.isBookmarked) FactPediaIcons.BookMark else FactPediaIcons.BookMarkBorder,
                        contentDescription = "Bookmark",
                        onClick = { onBookmarkClick(!fact.isBookmarked) }
                    )
                }
            }
        }
    }
}