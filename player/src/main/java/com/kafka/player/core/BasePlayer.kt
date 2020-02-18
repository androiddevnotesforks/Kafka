package com.kafka.player.core

import androidx.annotation.CallSuper
import com.kafka.player.model.*

/**
 * @author Vipul Kumar; dated 05/03/19.
 */
abstract class BasePlayer : Player {

    protected val basePlayerHelper = BasePlayerHelper()
    protected val seekUpdateInterval: Long = 1000
    private lateinit var playerExceptionHandler: PlayerExceptionHandler
    var lastPlayerState: PlayerState? = null

    init {
        updatePlayerState(PlayerState.Stopped(false))
    }

    protected fun isActionValid(
        playerAction: PlayerAction,
        playerState: PlayerState
    ) =
        basePlayerHelper.isActionValid(playerAction, playerState)

    protected fun updatePlayerState(playerState: PlayerState) {
        lastPlayerState = playerState
//        playerStateObservable.onNext(playerState)
    }

    internal fun getPlayerState() = lastPlayerState ?: noPlayerStateError

    internal fun updatePlayerSeekInfo(playerSeekInfo: PlayerSeekInfo) {
//        playerSeekInfoObservable.onNext(playerSeekInfo)
    }

    private fun validateStateChange(playerAction: PlayerAction) {
        if (basePlayerHelper.isActionValid(playerAction, getPlayerState())) {
            //valid state change
        } else {
            val exception =
                PlayerException("action [${playerAction.name}] not allowed when player is in ${getPlayerState()} state")
            if (::playerExceptionHandler.isInitialized) {
                playerExceptionHandler.onPlayerExceptionSwallowed(exception)
            } else {
                throw exception
            }
        }
    }

    fun setPlayerExceptionHandler(playerExceptionHandler: PlayerExceptionHandler) {
        this.playerExceptionHandler = playerExceptionHandler
    }

    @CallSuper
    override fun load(playbackItem: PlaybackItem, playerConfig: PlayerConfig) {
        validateStateChange(PlayerAction.INIT)
//        PlayerAnalytics.resetCounters()
    }

    @CallSuper
    override fun play() {
        validateStateChange(PlayerAction.PLAY)
    }

    @CallSuper
    override fun pause() {
        validateStateChange(PlayerAction.PAUSE)
    }

    @CallSuper
    override fun stop() {
        validateStateChange(PlayerAction.STOP)
    }

    @CallSuper
    override fun seekTo(seekPositionInMs: Long) {
        validateStateChange(PlayerAction.SEEK)
    }

    @CallSuper
    override fun setVolume(volume: Float) {
        validateStateChange(PlayerAction.CHANGE_VOLUME)
    }

    @CallSuper
    override fun destroy() {
        validateStateChange(PlayerAction.DESTROY)
    }

    abstract fun updatePlayerConfig(playerConfig: PlayerConfig)

    enum class PlayerAction {
        INIT, PLAY, PAUSE, STOP, SEEK, DESTROY, CHANGE_VOLUME
    }

    interface PlayerExceptionHandler {
        fun onPlayerExceptionSwallowed(playerException: PlayerException)
    }
}
