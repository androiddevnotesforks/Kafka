package com.kafka.data.feature.common

import com.kafka.data.data.config.kodeinInstance
import com.kafka.data.data.api.ArchiveService
import com.kafka.data.data.api.RetrofitRunner
import org.kodein.di.generic.instance

/**
 * @author Vipul Kumar; dated 02/01/19.
 */
open class DataSource {
    protected val archiveService: ArchiveService by kodeinInstance.instance()
    protected val retrofitRunner: RetrofitRunner by kodeinInstance.instance()
}
