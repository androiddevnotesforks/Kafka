package com.kafka.player.domain

import com.data.base.AppCoroutineDispatchers
import com.data.base.SubjectInteractor
import com.data.base.extensions.debug
import com.kafka.player.exo.PlayerLifecycle
import com.kafka.player.timber.models.Song
import com.kafka.player.timber.playback.players.SongPlayer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObservePlayer @Inject constructor(
    dispatchers: AppCoroutineDispatchers,
    private val songPlayer: SongPlayer,
    private val playerLifecycle: PlayerLifecycle
) : SubjectInteractor<Unit, PlayerData>() {
    override val dispatcher: CoroutineDispatcher = dispatchers.io

    override fun createObservable(params: Unit): Flow<PlayerData> {
        playerLifecycle.onStart()
        return songPlayer.songChannel.asFlow().map { song -> song.asPlayerData() }
    }

    private fun Song.asPlayerData() = PlayerData(id, isPlaying(), seekFlow(), title, artist, coverImage)
    private fun Song.isPlaying() = true
    private fun Song.seekFlow(): Flow<Int> = songPlayer.seekPositionFlow.map {
        debug { "seek progress is $it" }
        if (duration != 0) {
            (it / duration) * 100
        } else 0
    }
}
