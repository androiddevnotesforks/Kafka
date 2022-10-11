package org.kafka.common.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import org.kafka.common.R
import org.kafka.common.UiMessage
import org.kafka.common.extensions.AnimatedVisibility
import org.kafka.common.extensions.alignCenter
import ui.common.theme.theme.body2

@Composable
fun FullScreenMessage(
    uiError: UiMessage?,
    show: Boolean = uiError != null,
    onRetry: (() -> Unit)? = null
) {
    AnimatedVisibility(visible = show) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LoadImage(
                data = R.drawable.img_absurd_error,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(0.7f)
                    .padding(48.dp),
                tint = MaterialTheme.colorScheme.secondary
            )

            Column(
                modifier = Modifier.weight(0.3f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Something went wrong",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = uiError?.message.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium.alignCenter()
                )

                onRetry?.let {
                    Spacer(modifier = Modifier.height(24.dp))

                    TextButton(onClick = onRetry) {
                        Text(
                            text = "Retry",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(
    snackbarError: String?,
    modifier: Modifier = Modifier,
    show: Boolean = snackbarError != null,
) {
    if (show) {
        Card(
            modifier = modifier,
            elevation = 1.dp,
            shape = RectangleShape,
            backgroundColor = MaterialTheme.colorScheme.error
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = snackbarError.orEmpty(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
