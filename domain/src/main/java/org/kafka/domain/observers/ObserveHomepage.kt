package org.kafka.domain.observers

import com.kafka.data.entities.Homepage
import com.kafka.data.model.ArchiveQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import org.kafka.base.AppCoroutineDispatchers
import org.kafka.base.domain.SubjectInteractor
import javax.inject.Inject

class ObserveHomepage @Inject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val observeRecentItems: ObserveRecentItems,
    private val observeFollowedItems: ObserveFollowedItems,
    private val observeQueryItems: ObserveQueryItems
) : SubjectInteractor<ArchiveQuery, Homepage>() {

    override fun createObservable(params: ArchiveQuery): Flow<Homepage> {
        observeQueryItems(ObserveQueryItems.Params(params))
        observeRecentItems(Unit)
        observeFollowedItems(Unit)

        return combine(
            observeQueryItems.observe(),
            observeRecentItems.observe(),
            observeFollowedItems.observe(),
        ) { queryItems, recentItems, followedItems ->
            Homepage(queryItems, recentItems, followedItems)
        }.flowOn(appCoroutineDispatchers.io)
    }
}
