package org.kafka.homepage

import androidx.compose.runtime.Immutable
import com.kafka.data.entities.Homepage
import org.kafka.common.UiMessage

@Immutable
data class HomepageViewState(
    val homepage: Homepage? = null,
    val isLoading: Boolean = false,
    val message: UiMessage? = null
) {
    val isFullScreenLoading = isLoading && !homepage?.queryItems.isNullOrEmpty() && message == null
    val isFullScreenError = homepage == null && message != null
}
