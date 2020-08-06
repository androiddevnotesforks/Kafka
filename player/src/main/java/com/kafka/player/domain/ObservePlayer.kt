package com.kafka.player.domain

import com.data.base.AppCoroutineDispatchers
import com.data.base.SubjectInteractor
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
    private val songPlayer: SongPlayer
) : SubjectInteractor<Unit, PlayerData>() {
    override val dispatcher: CoroutineDispatcher = dispatchers.io

    override fun createObservable(params: Unit): Flow<PlayerData> {
        return songPlayer.songChannel.asFlow().map { song -> song.asPlayerData() }
    }

    private fun Song.asPlayerData() = PlayerData(id, isPlaying(), seekFlow(), title, artist, album)
    private fun Song.isPlaying() = true
    private fun Song.seekFlow() = songPlayer.seekPositionFlow.map { (it / duration) * 100 }
}
