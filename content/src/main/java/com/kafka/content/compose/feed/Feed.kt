package com.kafka.content.compose.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.ExperimentalLazyDsl
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.kafka.content.compose.Actions
import com.kafka.content.compose.item.ContentItem
import com.kafka.content.data.Homepage
import com.kafka.content.ui.homepage.HomepageViewModel
import com.kafka.content.ui.homepage.bannerImages
import com.kafka.content.ui.query.ArchiveQueryViewModel
import com.kafka.data.entities.Item
import com.kafka.ui_common.theme.KafkaColors
import dev.chrisbanes.accompanist.coil.CoilImage

@ExperimentalLazyDsl
@Composable
fun Feed(actions: Actions) {
    val feedViewModel: HomepageViewModel = viewModel()
    val queryViewModel: ArchiveQueryViewModel = viewModel()
    val feedViewState by feedViewModel.state.collectAsState()

    remember(feedViewModel.selectedQuery) { queryViewModel.submitQuery(feedViewModel.selectedQuery) }

    feedViewState.homepage?.let { Feed(it, actions) }
}

@ExperimentalLazyDsl
@Composable
fun Feed(homepage: Homepage, actions: Actions) {
    val itemDetailAction: (Item) -> Unit = { actions.itemDetail(it.itemId) }
    Surface(color = KafkaColors.background) {
        LazyColumn(content = {
            item { ContinueListening(items = homepage.recentItems, onItemClick = actions.itemDetail) }
            item { Favorites(favorites = homepage.followedItems, onItemClick = actions.itemDetail) }

            items(items = listOf(0)) {
                LazyRowFor(items = bannerImages) {
                    Banner(it = it)
                }
            }
            items(items = homepage.queryItems) {
                ContentItem(item = it, onItemClick = itemDetailAction)
            }
        })
    }
}

@Composable
fun Banner(it: Int) {
    Row {
        Card(
            modifier = Modifier.size(396.dp, 196.dp).padding(12.dp),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color(0xFF000000)
        ) {
            Box() {
                CoilImage(data = it, fadeIn = true, contentScale = ContentScale.Crop)
            }
        }
    }
}

