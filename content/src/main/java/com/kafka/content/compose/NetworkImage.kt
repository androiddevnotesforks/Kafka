package com.kafka.content.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.kafka.ui.theme.KafkaColors
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderColor: Color? = KafkaColors.surface
) {
    CoilImage(
        data = url,
        modifier = modifier,
        fadeIn = true,
        contentScale = contentScale,
        loading = {
            if (placeholderColor != null) {
                Spacer(modifier = Modifier.fillMaxSize().background(placeholderColor))
            }
        }
    )
}
