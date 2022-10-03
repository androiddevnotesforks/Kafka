package com.kafka.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kafka.data.dao.FollowedItemDao
import com.kafka.data.dao.ItemDao
import com.kafka.data.dao.ItemDetailDao
import com.kafka.data.dao.LanguageDao
import com.kafka.data.dao.QueueDao
import com.kafka.data.dao.RecentItemDao
import com.kafka.data.dao.RecentSearchDao
import com.kafka.data.dao.SearchDao
import com.kafka.data.entities.FollowedItem
import com.kafka.data.entities.Item
import com.kafka.data.entities.ItemDetail
import com.kafka.data.entities.Language
import com.kafka.data.entities.QueueEntity
import com.kafka.data.entities.RecentItem
import com.kafka.data.entities.RecentSearch
import com.kafka.data.entities.Song

interface KafkaDatabase {
    fun itemDetailDao(): ItemDetailDao
    fun recentItemDao(): RecentItemDao
    fun followedItemDao(): FollowedItemDao
    fun searchDao(): SearchDao
    fun itemDao(): ItemDao
    fun queueDao(): QueueDao
    fun languageDao(): LanguageDao
    fun searchConfigurationDao(): RecentSearchDao
}

@Database(
    entities = [
        ItemDetail::class,
        Item::class,
        Language::class,
        RecentItem::class,
        FollowedItem::class,
        QueueEntity::class,
        Song::class,
        RecentSearch::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(AppTypeConverters::class)
abstract class KafkaRoomDatabase : RoomDatabase(), KafkaDatabase
