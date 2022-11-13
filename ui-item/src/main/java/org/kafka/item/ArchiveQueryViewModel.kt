package org.kafka.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kafka.data.model.ArchiveQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.kafka.base.extensions.stateInDefault
import org.kafka.common.ObservableLoadingCounter
import org.kafka.common.UiMessageManager
import org.kafka.common.collectStatus
import org.kafka.domain.interactors.SearchQuery
import org.kafka.domain.interactors.SearchQueryType
import org.kafka.domain.interactors.UpdateItems
import org.kafka.domain.interactors.asArchiveQuery
import org.kafka.domain.observers.ObserveQueryItems
import javax.inject.Inject

@HiltViewModel
class ArchiveQueryViewModel @Inject constructor(
    private val observeQueryItems: ObserveQueryItems,
    private val updateItems: UpdateItems
) : ViewModel() {
    private val loadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    val state: StateFlow<ArchiveQueryViewState> = combine(
        observeQueryItems.flow,
        loadingState.observable,
        uiMessageManager.message,
    ) { items, isLoading, message ->
        ArchiveQueryViewState(
            items = items,
            message = message,
            isLoading = isLoading
        )
    }.stateInDefault(
        scope = viewModelScope,
        initialValue = ArchiveQueryViewState(),
    )

    fun submitQuery(keyword: String) {
        submitQuery(SearchQuery(keyword, SearchQueryType.Search))
    }

    private fun submitQuery(searchQuery: SearchQuery) {
        submitQuery(searchQuery.asArchiveQuery())
    }

    private fun submitQuery(query: ArchiveQuery) {
        observeQueryItems(ObserveQueryItems.Params(query))
        viewModelScope.launch {
            updateItems(UpdateItems.Params(query)).collectStatus(loadingState, uiMessageManager)
        }
    }
}
