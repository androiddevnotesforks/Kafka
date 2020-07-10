package com.kafka.content.data.detail

import com.data.base.model.getOrThrow
import com.kafka.data.dao.ItemDetailLocalDataSource
import com.kafka.data.extensions.asyncOrAwait
import javax.inject.Inject

/**
 * @author Vipul Kumar; dated 29/11/18.
 *
 */
class ItemDetailRepository @Inject constructor(
    private val itemDetailLocalDataSource: ItemDetailLocalDataSource,
    private val itemDetailRemoteDataSource: com.kafka.content.data.detail.ItemDetailRemoteDataSource
) {

    fun observeItemDetail(itemId: String) = itemDetailLocalDataSource.itemDetailFlow(itemId)

    fun getItemDetail(itemId: String) = itemDetailLocalDataSource.itemDetail(itemId)

    suspend fun updateItemDetail(contentId: String) {
        asyncOrAwait(key = "updateContentDetail") {
            itemDetailRemoteDataSource.fetchItemDetail(contentId)
                .getOrThrow()
                .let { itemDetailLocalDataSource.insert(it) }
        }
    }
}
