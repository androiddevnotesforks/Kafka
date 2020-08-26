package com.kafka.content.ui.query

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.data.base.InvokeStatus
import com.data.base.launchObserve
import com.kafka.content.domain.item.ObserveItems
import com.kafka.content.domain.item.UpdateItems
import com.kafka.data.model.ObservableLoadingCounter
import com.kafka.ui_common.base.ReduxViewModel
import com.kafka.ui_common.base.SnackbarManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ArchiveQueryViewModel @ViewModelInject constructor(
    private val observeItems: ObserveItems,
    private val updateItems: UpdateItems,
    private val loadingState: ObservableLoadingCounter,
    private val snackbarManager: SnackbarManager
) : ReduxViewModel<ArchiveQueryViewState>(ArchiveQueryViewState()) {

    init {
        viewModelScope.launchObserve(observeItems) { flow ->
            flow.distinctUntilChanged().collectAndSetState { list ->
                copy(items = list)
            }
        }

        viewModelScope.launch { loadingState.observable.collectAndSetState { copy(isLoading = it) } }

        snackbarManager.launchInScope(viewModelScope) { uiError, visible ->
            viewModelScope.launchSetState {
                copy(error = if (visible) uiError else null)
            }
        }
    }

    fun submitQuery(searchQuery: SearchQuery) {
        val query = searchQuery.asArchiveQuery()

        observeItems(ObserveItems.Params(query))
        viewModelScope.launch {
            updateItems(UpdateItems.Params(query)).watchStatus()
        }
    }

    private fun Flow<InvokeStatus>.watchStatus() = viewModelScope.launch { collectStatus() }

    private suspend fun Flow<InvokeStatus>.collectStatus() = collect { status ->
        when (status) {
            InvokeStatus.Loading -> loadingState.addLoader()
            InvokeStatus.Success -> loadingState.removeLoader()
            is InvokeStatus.Error -> {
                snackbarManager.sendError(status.throwable)
                loadingState.removeLoader()
            }
        }
    }
}

