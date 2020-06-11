package com.kafka.data.injection

import android.content.Context
import android.os.Debug
import androidx.room.Room
import com.kafka.data.data.db.KafkaDatabase
import com.kafka.data.data.db.KafkaRoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton
import kotlin.coroutines.EmptyCoroutineContext

const val databaseName = "kafka.db"

@Module(
    includes = [
        RoomDatabaseModule::class,
        DatabaseDaoModule::class,
        DatabaseModuleBinds::class
    ]
)
class DatabaseModule

@Module
class RoomDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): KafkaRoomDatabase {
        val builder = Room.databaseBuilder(
            context, KafkaRoomDatabase::class.java, databaseName
        ).fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    @ForStore
    @Singleton
    @Provides
    fun providesStoreDispatcher(): CoroutineScope = CoroutineScope(EmptyCoroutineContext)
}


@Module
abstract class DatabaseModuleBinds {

    @Singleton
    @Binds
    abstract fun provideRoomDatabase(bind: KafkaRoomDatabase): KafkaDatabase
}

@Module
class DatabaseDaoModule {
    @Provides
    fun providePoetsDao(db: KafkaRoomDatabase) = db.queryDao()

    @Provides
    fun providePoetEntryDao(db: KafkaRoomDatabase) = db.itemDetailDao()

    @Provides
    fun provideContentDao(db: KafkaRoomDatabase) = db.languageDao()

    @Provides
    fun provideContentEntryDao(db: KafkaRoomDatabase) = db.searchDao()

    @Provides
    fun provideRecentItemDao(db: KafkaRoomDatabase) = db.recentItemDao()
}
