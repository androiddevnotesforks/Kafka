package com.kafka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val mediaTypeText = "texts"
const val mediaTypeAudio = "audio"

@Entity
data class File(
    @PrimaryKey val fileId: String,
    val itemId: String,
    val size: String?,
    val title: String?,
    val extension: String?,
    val creator: String?,
    val time: Long,
    val format: String?,
    val playbackUrl: String?,
    val readerUrl: String?,
    val localUri: String? = null
): BaseEntity

fun ItemDetail?.isText() = this?.mediaType == mediaTypeText
fun ItemDetail?.isAudio() = this?.mediaType == mediaTypeAudio
fun ItemDetail?.hasAudio() = this?.files?.firstOrNull { it.isMp3() } != null
fun ItemDetail?.hasText() = this?.files?.firstOrNull { it.isPdf() } != null

//fun File.formattedDuration() = Duration.ofMillis(time).formattedDuration()

fun List<File>.filterMp3() = this.filter { it.isMp3() }

fun File.isPdf() = format?.isPdf() ?: false

fun File.isMp3() = format?.isMp3() ?: false

fun File.isCoverImage() = format?.contains("JPEG", true) ?: false ||
        format?.endsWith("Tile", true) ?: false

fun String?.isPdf() = this?.contains("pdf", true) ?: false

fun String?.isText() = this?.contains("txt", true) ?: false

fun String?.isMp3() = this?.contains("mp3", true) ?: false
