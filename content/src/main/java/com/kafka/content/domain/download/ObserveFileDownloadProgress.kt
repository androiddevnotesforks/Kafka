package com.kafka.content.domain.download

import androidx.work.WorkManager
import com.data.base.AppCoroutineDispatchers
import com.data.base.SubjectInteractor
import com.kafka.content.work.DownloadFileWorker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//class ObserveFileDownloadProgress @Inject constructor(
//    dispatchers: AppCoroutineDispatchers,
//    private val workManager: WorkManager
//) : SubjectInteractor<Unit, String>() {
//    override val dispatcher: CoroutineDispatcher = dispatchers.io
//
//    override fun createObservable(params: Unit): Flow<String> {
//       return workManager.getWorkInfosByTag(DownloadFileWorker.TAG)
//    }
//
//}
