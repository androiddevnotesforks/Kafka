package com.kafka.ui.player

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.Surface
import androidx.ui.unit.dp
import com.kafka.data.model.item.File
import com.kafka.ui.*
import com.kafka.ui.R

@Composable
fun PlayerQueue(files: List<File>, actioner: (PlayerAction) -> Unit) {
    AdapterList(data = files) {
        File(it = it, actioner = actioner)
    }
}

@Composable
fun File(it: File?, actioner: (PlayerAction) -> Unit) {
    val title = it?.name ?: ""
    val subtitle = it?.artist + " ∙ " + it?.mtime
    Clickable(onClick = { actioner(Play(it?.original ?: "")) }) {
        Column(modifier = Modifier.paddingHV(horizontal = 24.dp, vertical = 24.dp).fillMaxWidth()) {
            Row {
                Surface(modifier = Modifier.gravity(RowAlign.Center), color = colors().background) {
                    VectorImage(id = R.drawable.ic_headphones, tint = colors().primary)
                }
                Spacer(modifier = Modifier.preferredWidth(16.dp))
                Column(modifier = Modifier.gravity(RowAlign.Center)) {
                    Text(text = title, style = typography().body1.lineHeight(1.3))
                    Text(text = subtitle, style = typography().caption.lineHeight(1.3))
                }
            }
        }
    }
}
