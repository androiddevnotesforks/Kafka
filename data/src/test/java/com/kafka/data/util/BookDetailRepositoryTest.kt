package com.kafka.data.util

import com.kafka.data.item.BookDataSource
import com.kafka.data.item.BookRepository
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

/**
 * @author Vipul Kumar; dated 11/02/19.
 */

class BookDetailRepositoryTest : BaseDatabaseTest() {
    private lateinit var detailDao: BookDao

    private lateinit var dataSource: BookDataSource
    private lateinit var repository: BookRepository

    override fun setup() {
        super.setup()
        // We'll assume that there's a show in the db
        insertShow(db)

        followShowsDao = db.followedShowsDao()

        showRepository = Mockito.mock(ShowRepository::class.java)
        Mockito.`when`(showRepository.needsUpdate(ArgumentMatchers.any(Long::class.java))).thenReturn(true)

        traktDataSource = Mockito.mock(FollowedShowsDataSource::class.java)

        val txRunner = RoomTransactionRunner(db)

        repository = FollowedShowsRepository(
            testCoroutineDispatchers,
            LocalFollowedShowsStore(txRunner, EntityInserter(txRunner),
                db.followedShowsDao(), db.showDao(), db.lastRequestDao()),
            LocalShowStore(EntityInserter(txRunner), db.showDao(), db.lastRequestDao(), txRunner),
            traktDataSource,
            showRepository,
            Provider { TraktAuthState.LOGGED_IN }
        )
    }
