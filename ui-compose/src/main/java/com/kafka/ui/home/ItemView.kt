package com.kafka.ui.home

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.foundation.Border
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.unit.dp
import com.kafka.data.entities.Item
import com.kafka.data.extensions.getRandomAuthorResource
import com.kafka.ui.*
import com.kafka.ui.R

@Composable
fun ContentItem(content: Item, onItemClick: (Item) -> Unit) {
    val size = 184.dp
    Column(
        modifier = Modifier.padding(12.dp).clickable(onClick = { onItemClick(content) })
    ) {
        Card(
            modifier = Modifier.preferredSize(size),
            shape = RoundedCornerShape(4.dp),
            border = Border(1.dp, colors().surface),
            elevation = 0.dp
        ) {
            Image(asset = imageResource(id = content.coverImageResource), contentScale = ContentScale.Crop)
        }

        Text(
            text = content.title ?: "",
            modifier = Modifier.preferredWidth(size).paddingHV(horizontal = 8.dp, vertical = 12.dp),
            maxLines = 2,
            style = MaterialTheme.typography.subtitle2.lineHeight(1.3),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ContentItemList(content: Item, onItemClick: (Item) -> Unit) {
    Stack(modifier = Modifier.clickable(onClick = { onItemClick(content) }, indication = null)) {
        Row(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
            Card(
                modifier = Modifier.preferredSize(86.dp, 106.dp),
                shape = RoundedCornerShape(2.dp),
                elevation = 1.dp
            ) {
                Image(asset = imageResource(id = getRandomAuthorResource()), contentScale = ContentScale.Crop)
            }
            Column(modifier = Modifier.paddingHV(horizontal = 16.dp, vertical = 4.dp)) {
                Text(
                    text = content.title ?: "",
                    modifier = Modifier,
                    maxLines = 2,
                    style = MaterialTheme.typography.subtitle1.lineHeight(1.3),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = listOfNotNull(content.creator, content.language?.joinToString())
                        .joinToString(separator = bulletSymbol),
                    modifier = Modifier.padding(top = 4.dp),
                    maxLines = 2,
                    style = MaterialTheme.typography.caption.alpha(alpha = 0.6f)
                )
            }
        }
        Row(modifier = Modifier.gravity(Alignment.BottomEnd).padding(24.dp)) {
            VectorImage(id = R.drawable.ic_layers, size = 16.dp, tint = Color(0x88FDD901))
            Spacer(modifier = Modifier.width(24.dp))
            VectorImage(id = R.drawable.ic_headphones, size = 16.dp, tint = Color(0x8825C681))
        }
    }
}
