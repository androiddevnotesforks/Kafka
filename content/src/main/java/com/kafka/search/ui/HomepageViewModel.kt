package com.kafka.search.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.data.base.launchObserve
import com.kafka.data.query.ArchiveQuery
import com.kafka.data.query.booksByAuthor
import com.kafka.domain.ObservableLoadingCounter
import com.kafka.domain.item.ObserveBatchItems
import com.kafka.language.domain.ObserveSelectedLanguages
import com.kafka.ui.actions.HomepageAction
import com.kafka.ui.actions.SubmitQueryAction
import com.kafka.ui.search.HomepageViewState
import com.kafka.ui_common.BaseComposeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomepageViewModel @ViewModelInject constructor(
    private val observeBatchItems: ObserveBatchItems,
    private val loadingState: ObservableLoadingCounter,
    observeSelectedLanguages: ObserveSelectedLanguages
) : BaseComposeViewModel<HomepageViewState>(HomepageViewState()) {
    val pendingActions = Channel<HomepageAction>(Channel.BUFFERED)

    init {
        viewModelScope.launch(Dispatchers.Main) {
            loadingState.observable
                .distinctUntilChanged()
                .collect {
                    setState { copy(isLoading = it) }
                }
        }

        viewModelScope.launchObserve(observeSelectedLanguages) { flow ->
            flow.distinctUntilChanged().execute { selectedLanguages = it }
        }

        observeSelectedLanguages(Unit)

        viewModelScope.launchObserve(observeBatchItems) { flow ->
            flow.distinctUntilChanged().execute {
                it.let { items = it }
            }
        }

        viewModelScope.launch {
            for (action in pendingActions) when (action) {
                is SubmitQueryAction -> submitQuery(action.query)
            }
        }
    }

    fun submitAction(action: HomepageAction) {
        viewModelScope.launch { pendingActions.send(action) }
    }

    private fun submitQuery(searchQuery: String) {
        setState { copy(query = searchQuery) }
        listOf(
            ArchiveQuery().booksByAuthor(searchQuery)
        ).let {
            observeQuery(it)
        }
    }

    private fun observeQuery(queries: List<ArchiveQuery>) {
        observeBatchItems(ObserveBatchItems.Params(queries))
    }
}

