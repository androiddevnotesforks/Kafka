package org.kafka.item.files

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kafka.data.entities.File
import org.kafka.common.Icons
import org.kafka.common.extensions.elevation
import org.kafka.common.extensions.rememberStateWithLifecycle
import org.kafka.common.widgets.IconResource
import org.kafka.navigation.LeafScreen.Reader
import org.kafka.navigation.LocalNavigator
import org.kafka.navigation.Navigator
import ui.common.theme.theme.textPrimary

@Composable
fun Files(viewModel: FilesViewModel = hiltViewModel()) {
    val viewState by rememberStateWithLifecycle(viewModel.state)
    val navigator = LocalNavigator.current

    Scaffold(topBar = { TopBar() }) { padding ->
        LazyColumn(modifier = Modifier, contentPadding = padding) {
            items(viewState.files, key = { it.fileId }) {
                File(
                    it = it,
                    isLoading = viewState.isLoading,
                    startDownload = { viewModel.downloadFile(it.fileId) },
                    openReader = {
                        navigator.navigate(Reader.createRoute(viewModel.downloadState.value.toString()))
                    }
                )
            }
        }
    }
}

@Composable
private fun File(it: File, isLoading: Boolean, startDownload: () -> Unit, openReader: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openReader() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = it.title.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.textPrimary,
                modifier = Modifier
            )

            Text(
                text = it.extension + " - " + it.size.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
            )
        }

        IconButton(onClick = startDownload) {
            IconResource(imageVector = Icons.Download, tint = MaterialTheme.colorScheme.primary)
        }
    }

}

@Composable
private fun TopBar(
    lazyListState: LazyListState = rememberLazyListState(),
    navigator: Navigator = LocalNavigator.current
) {
    org.kafka.ui.components.material.TopBar(
        navigationIcon = {
            IconButton(onClick = { navigator.back() }) {
                IconResource(imageVector = Icons.Back, tint = MaterialTheme.colorScheme.primary)
            }
        },
        elevation = remember { lazyListState.elevation }
    )
}
