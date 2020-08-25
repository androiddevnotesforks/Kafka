///*
// * Copyright (c) 2019 Naman Dwivedi.
// *
// * Licensed under the GNU General Public License v3
// *
// * This is free software: you can redistribute it and/or modify it
// * under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// *
// * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
// * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
// * See the GNU General Public License for more details.
// *
// */
//package com.kafka.player.timber.repository
//
//import android.annotation.SuppressLint
//import android.content.ContentResolver
//import android.database.Cursor
//import android.provider.BaseColumns._ID
//import android.provider.MediaStore
//import android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//import com.kafka.data.entities.Song
//import com.kafka.player.timber.constants.SongSortOrder
//import timber.log.Timber
//import java.io.File
//import javax.inject.Inject
//import javax.inject.Singleton
//import android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI as AUDIO_URI
//
//interface SongsRepository {
//
//    fun loadSongs(caller: String?): List<Song>
//
//    fun getSongForId(id: Long): Song
//
//    fun getSongsForIds(idList: LongArray): List<Song>
//
//    fun getSongFromPath(songPath: String): Song
//
//    fun searchSongs(searchString: String, limit: Int): List<Song>
//
//    fun deleteTracks(ids: LongArray): Int
//}
//
//@Singleton
//class RealSongsRepository @Inject constructor(
//    private val contentResolver: ContentResolver
//) : SongsRepository {
//
//    override fun loadSongs(caller: String?): List<Song>  = listOf()
//
//    override fun getSongForId(id: Long): Song {
//        return Song()
//    }
//
//    override fun getSongsForIds(idList: LongArray): List<Song> = listOf()
//
//    override fun getSongFromPath(songPath: String): Song {
//        throw IllegalStateException("Unable to query , system returned null.")
//    }
//
//    override fun searchSongs(searchString: String, limit: Int): List<Song> = listOf()
//
//    // TODO a lot of operations are done here without verifying results,
//    // TODO e.g. if moveToFirst() returns true...
//    override fun deleteTracks(ids: LongArray): Int {
//        val projection = arrayOf(
//                _ID,
//                MediaStore.MediaColumns.DATA,
//                MediaStore.Audio.AudioColumns.ALBUM_ID
//        )
//        val selection = StringBuilder().apply {
//            append("$_ID IN (")
//            for (i in ids.indices) {
//                append(ids[i])
//                if (i < ids.size - 1) {
//                    append(",")
//                }
//            }
//            append(")")
//        }
//
//        contentResolver.query(
//                AUDIO_URI,
//                projection,
//                selection.toString(),
//                null,
//                null
//        )?.use {
//            it.moveToFirst()
//            // Step 2: Remove selected tracks from the database
//            contentResolver.delete(AUDIO_URI, selection.toString(), null)
//
//            // Step 3: Remove files from card
//            it.moveToFirst()
//            while (!it.isAfterLast) {
//                val name = it.getString(1)
//                val f = File(name)
//                try { // File.delete can throw a security exception
//                    if (!f.delete()) {
//                        // I'm not sure if we'd ever get here (deletion would
//                        // have to fail, but no exception thrown)
//                        Timber.d("Failed to delete file: $name")
//                    }
//                } catch (_: SecurityException) {
//                }
//                it.moveToNext()
//            }
//        }
//
//        return ids.size
//    }
//
//    private fun makeSongCursor(selection: String?, paramArrayOfString: Array<String>?): Cursor {
//        return makeSongCursor(selection, paramArrayOfString, SongSortOrder.SONG_A_Z.rawValue)
//    }
//
//    @SuppressLint("Recycle")
//    private fun makeSongCursor(selection: String?, paramArrayOfString: Array<String>?, sortOrder: String?): Cursor {
//        val selectionStatement = StringBuilder("is_music=1 AND title != ''")
//        if (!selection.isNullOrEmpty()) {
//            selectionStatement.append(" AND $selection")
//        }
//        val projection = arrayOf("_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id")
//
//        return contentResolver.query(
//                EXTERNAL_CONTENT_URI,
//                projection,
//                selectionStatement.toString(),
//                paramArrayOfString,
//                sortOrder
//        ) ?: throw IllegalStateException("Unable to query $EXTERNAL_CONTENT_URI, system returned null.")
//    }
//}
