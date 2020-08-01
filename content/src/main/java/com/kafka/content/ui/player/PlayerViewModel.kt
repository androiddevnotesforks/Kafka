package com.kafka.content.ui.player

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.data.base.launchObserve
import com.kafka.content.domain.detail.ObserveItemDetail
import com.kafka.data.model.ObservableLoadingCounter
import com.kafka.player.domain.CommandPlayer
import com.kafka.player.domain.ObservePlayer
import com.kafka.player.domain.PlayerCommand
import com.kafka.player.domain.PlayerViewState
import com.kafka.ui_common.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class PlayerViewModel @ViewModelInject constructor(
    private val loadingState: ObservableLoadingCounter,
    private val commandPlayer: CommandPlayer,
    observePlayer: ObservePlayer,
    observeItemDetail: ObserveItemDetail
) : BaseViewModel<PlayerViewState>(PlayerViewState()) {

    private val pendingActions = Channel<PlayerCommand>(Channel.BUFFERED)

    init {
        viewModelScope.launch {
            loadingState.observable.distinctUntilChanged().collect {
                setState { copy(isLoading = it) }
            }
        }

        viewModelScope.launchObserve(observeItemDetail) { it.execute { copy(itemDetail = it) } }
        viewModelScope.launchObserve(observePlayer) { it.execute { copy(playerData = it) } }

        viewModelScope.launch {
            for (action in pendingActions) when (action) {
                is PlayerCommand.Play -> commandPlayer(PlayerCommand.Play(action.itemId))
                is PlayerCommand.ToggleCurrent -> commandPlayer(
                    PlayerCommand.ToggleCurrent)
            }
        }

        observeItemDetail(ObserveItemDetail.Param(""))
        observePlayer(Unit)
    }

    fun submitAction(command: PlayerCommand) {
        viewModelScope.launch { pendingActions.send(command) }
    }
}

