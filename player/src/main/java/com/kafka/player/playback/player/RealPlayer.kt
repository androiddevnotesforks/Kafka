package com.kafka.player.playback.player

import android.content.Context
import com.data.base.extensions.debug
import com.data.base.extensions.e
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Player.*
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.kafka.data.CustomScope
import com.kafka.data.dao.QueueDao
import com.kafka.data.entities.QueueEntity
import com.kafka.data.entities.Song
import com.kafka.player.playback.extensions.prepare
import com.kafka.player.playback.extensions.setup
import com.kafka.player.playback.extensions.toMediaSources
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealPlayer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val queueDao: QueueDao
) : Player, CoroutineScope by CustomScope() {

    private val player: SimpleExoPlayer by lazy { SimpleExoPlayer.Builder(context).setUseLazyPreparation(true).build() }
    private val concatenatingMediaSource by lazy { ConcatenatingMediaSource() }
    private val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, context.packageName))

    private val currentItem
        get() = (player.currentTag as? Song)

    init {
        launch {
            if (queueDao.getQueueEntity() == null) {
                queueDao.insertQueueEntity(QueueEntity())
            }
        }
    }

    override suspend fun setQueue(queue: List<Song>) {
        queueDao.clearSongs()
        queueDao.insertAll(queue)
        concatenatingMediaSource.clear()
        concatenatingMediaSource.addMediaSources(queue.toMediaSources(dataSourceFactory))
    }

    private fun initializePlayer() {
        player.setup {
//            onLoadingChange { debug { "$it" } }
            onError { e(it) { "" } }
            onTracksChanged { tracks, selections ->
//                debug { "track changed" }
            }
            onPositionDiscontinuity { onPositionDiscontinuity(it) }
            onTimelineChange { timeline, any ->
//                debug { "timeline" }
            }
            onSeek {
                debug { "seek" }
            }
            onPlayerState { playWhenReady, playbackState ->
                val item = currentItem
                if (item?.id != null) {
                    launch {
                        val duration = player.duration
                        val position = player.currentPosition
                        val seek = (position / duration) * 100
                        debug { "playing state changed $playbackState ${player.isPlaying} $duration $position" }
                        queueDao.updatePlayingStatus(player.isPlaying)
                        queueDao.updatePlayerSeekPosition(seek)
                    }
                }
            }
        }

        player.prepare(concatenatingMediaSource) {
            playWhenReady = false
            shuffleModeEnabled = false
            repeatMode = REPEAT_MODE_ALL
        }
    }

    private fun onPositionDiscontinuity(reason: Int?) {
        debug { "onPositionDiscontinuity" }
        launch {
            currentItem?.id?.let { queueDao.updateCurrentSong(it) }
        }
        when (reason) {
            DISCONTINUITY_REASON_PERIOD_TRANSITION -> {
            }
            DISCONTINUITY_REASON_SEEK -> {
            }
            TIMELINE_CHANGE_REASON_DYNAMIC -> {
            }
        }
    }

    override fun play() {
        player.playWhenReady = true
    }

    override fun play(song: Song) {
        launch {
            var position = queueDao.getQueueSongs().indexOfFirst { it?.id == song.id }
            if (position == -1) position = 0
            launch(Dispatchers.Main) {

                debug { "seek position $position" }
                player.apply {
                    playWhenReady = false
                    seekTo(position, C.TIME_UNSET)
                    playWhenReady = true
                }
            }
        }
    }

    override fun pause() {
        player.playWhenReady = false
    }

    override fun togglePlayPause() {
        if (player.isPlaying) {
            pause()
        } else {
            play()
        }
    }

    override fun next() {
        player.next()
    }

    override fun previous() {
        player.previous()
    }

    override fun seekTo(position: Long) {
        player.seekTo(position)
    }

    override fun start() {
        initializePlayer()
    }

    override fun stop() {

    }

    override fun release() {
        player.release()
    }
}
