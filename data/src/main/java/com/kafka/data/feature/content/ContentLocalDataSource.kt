package com.kafka.data.feature.content

import androidx.sqlite.db.SupportSQLiteQuery
import com.kafka.data.data.db.dao.ContentDao
import com.kafka.data.entities.Content
import javax.inject.Inject

/**
 * @author Vipul Kumar; dated 29/11/18.
 *
 */
class ContentLocalDataSource @Inject constructor(private val dao: ContentDao) {

    fun observeQueryByCreator(creator: String) = dao.observeQueryByCreator()

    fun observeQueryByCollection(collection: String) = dao.observeQueryByCollection("%$collection%")

    fun observeQueryByGenre(genre: String) = dao.observeQueryByGenre("%$genre%")

    fun observeQueryItems(sQLiteQuery: SupportSQLiteQuery) = dao.observeQueryByCreator()

    fun saveItems(contents: List<Content>) = dao.insertAll(contents)
}
