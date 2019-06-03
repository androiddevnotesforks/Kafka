package com.kafka.data.feature.book

import com.kafka.data.data.db.DatabaseTransactionRunner
import com.kafka.data.data.db.dao.BookDao
import com.kafka.data.entities.Book
import io.reactivex.Flowable

/**
 * @author Vipul Kumar; dated 29/11/18.
 *
 */
class LocalBookStore constructor(
    private val transactionRunner: DatabaseTransactionRunner,
    private val bookDao: BookDao
) {
    fun booksFlowable(path: String, searchKeyword: String): Flowable<List<Book>> {
        return bookDao.booksFlowable()
    }

    fun saveBooks(contendDetail: List<Book>) = transactionRunner {
        bookDao.insertBooks(contendDetail)
    }
}
