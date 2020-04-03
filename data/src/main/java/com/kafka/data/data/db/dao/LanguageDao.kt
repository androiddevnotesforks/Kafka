package com.kafka.data.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.kafka.data.entities.LanguageModel
import kotlinx.coroutines.flow.Flow

/**
 * @author Vipul Kumar; dated 29/11/18.
 */
@Dao
abstract class LanguageDao : EntityDao<LanguageModel> {
    @Query("select * from LanguageModel")
    abstract fun languageFlow(): Flow<List<LanguageModel>>
}
