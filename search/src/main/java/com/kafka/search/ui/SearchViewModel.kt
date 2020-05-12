package com.kafka.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.data.base.launchObserve
import com.kafka.data.entities.Item
import com.kafka.data.query.ArchiveQuery
import com.kafka.data.query.booksByAuthor
import com.kafka.domain.ObservableLoadingCounter
import com.kafka.domain.collectFrom
import com.kafka.domain.item.ObserveBatchItems
import com.kafka.domain.item.UpdateBatchItems
import com.kafka.language.domain.ObserveSelectedLanguages
import com.kafka.ui.actions.ItemClickAction
import com.kafka.ui.actions.SearchAction
import com.kafka.ui.actions.SubmitQueryAction
import com.kafka.ui.search.SearchViewState
import com.kafka.ui_common.BaseViewModel
import com.kafka.ui_common.Event
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val observeBatchItems: ObserveBatchItems,
    private val loadingState: ObservableLoadingCounter,
    private val updateBatchItems: UpdateBatchItems,
    observeSelectedLanguages: ObserveSelectedLanguages
) : BaseViewModel<SearchViewState>(SearchViewState()) {
    private val pendingActions = Channel<SearchAction>(Channel.BUFFERED)

    private val _navigateToContentDetailAction = MutableLiveData<Event<Item>>()
    val navigateToContentDetailAction: LiveData<Event<Item>>
        get() = _navigateToContentDetailAction

    init {
        viewModelScope.launch {
            loadingState.observable
                .distinctUntilChanged()
                .collect {
                    setState { copy(isLoading = it) }
                }
        }

        viewModelScope.launchObserve(observeSelectedLanguages) { flow ->
            flow.distinctUntilChanged().execute { items ->
                items.let { copy(selectedLanguages = it) }
            }
        }

        observeSelectedLanguages(Unit)

        viewModelScope.launchObserve(observeBatchItems) { flow ->
            flow.distinctUntilChanged().execute { items ->
                items.let { copy(items = it) }
            }
        }

        viewModelScope.launch {
            for (action in pendingActions) when (action) {
                is ItemClickAction -> _navigateToContentDetailAction.postValue(Event(action.item))
                is SubmitQueryAction -> submitQuery(action.query)
            }
        }
    }

    fun submitAction(action: SearchAction) {
        viewModelScope.launch { pendingActions.send(action) }
    }

    private fun submitQuery(searchQuery: String) {
        listOf(ArchiveQuery().booksByAuthor(searchQuery)).let {
            observeQuery(it)
            updateItems(it)
        }
    }

    private fun observeQuery(queries: List<ArchiveQuery>) {
        observeBatchItems(ObserveBatchItems.Params(queries))
    }

    fun updateItems(queries: List<ArchiveQuery>) {
        updateBatchItems(UpdateBatchItems.Params(queries))
            .also { viewModelScope.launch { loadingState.collectFrom(it) } }
    }
}

