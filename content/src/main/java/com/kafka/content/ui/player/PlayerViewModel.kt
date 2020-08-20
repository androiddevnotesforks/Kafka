package com.kafka.content.ui.player

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.data.base.launchObserve
import com.kafka.content.domain.detail.ObserveItemDetail
import com.kafka.content.domain.followed.ObserveItemFollowStatus
import com.kafka.content.domain.followed.UpdateFollowedItems
import com.kafka.data.entities.ItemDetail
import com.kafka.data.model.ObservableLoadingCounter
import com.kafka.player.domain.*
import com.kafka.ui_common.base.ReduxViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class PlayerViewModel @ViewModelInject constructor(
    private val loadingState: ObservableLoadingCounter,
    private val addPlaylistItems: AddPlaylistItems,
    private val commandPlayer: CommandPlayer,
    private val observeItemDetail: ObserveItemDetail,
    private val updateFollowedItems: UpdateFollowedItems,
    private val observeItemFollowStatus: ObserveItemFollowStatus,
    observePlayingItem: ObservePlayingItem
) : ReduxViewModel<PlayerViewState>(PlayerViewState()) {
    val pendingActions = Channel<PlayerAction>(Channel.BUFFERED)

    init {
        viewModelScope.launch {
            loadingState.observable.distinctUntilChanged().collectAndSetState { copy(isLoading = it) }
        }

        viewModelScope.launchObserve(observeItemDetail) {
            it.collectAndSetState { itemDetail ->
                itemDetail?.let { it ->
                    updatePlaylistOnInit(it)
                    observeItemFollowStatus(ObserveItemFollowStatus.Params(itemDetail.itemId))
                }
                copy(itemDetail = itemDetail)
            }
        }
        viewModelScope.launchObserve(observePlayingItem) { it.collectAndSetState { copy(mediaItem = it) } }

        viewModelScope.launchObserve(observeItemFollowStatus) { it.collectAndSetState { copy(isFavorite = it) } }

        viewModelScope.launch {
            for (action in pendingActions) when (action) {
                is PlayerAction.Command -> commandPlayer(action)
                is PlayerAction.FavoriteClick -> updateFollowedItems(UpdateFollowedItems.Params(currentState().itemDetail!!.itemId))
            }
        }

        observePlayingItem(Unit)
    }

    private fun updatePlaylistOnInit(itemDetail: ItemDetail) {
        addPlaylistItems(AddPlaylistItems.Param(itemDetail.itemId, true))

    }

    fun play(itemDetail: ItemDetail?) {
        itemDetail?.apply {
            submitAction(PlayerAction.Command(PlayerCommand.Play(itemId)))
        }
    }

    fun observeItemDetail(it: String) {
        observeItemDetail(ObserveItemDetail.Param(it))
    }

    fun submitAction(action: PlayerAction) {
        viewModelScope.launch { pendingActions.send(action) }
    }

    private fun commandPlayer(action: PlayerAction.Command) {
        when (val command = action.playerCommand) {
            is PlayerCommand.Play -> commandPlayer(PlayerCommand.Play(command.itemId, command.mediaId))
            is PlayerCommand.ToggleCurrent -> commandPlayer(PlayerCommand.ToggleCurrent)
        }
    }
}

