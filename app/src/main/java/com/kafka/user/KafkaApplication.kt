package com.kafka.user

import android.app.Application
import android.content.Context
import com.data.base.extensions.debug
import com.google.android.play.core.splitcompat.SplitCompat
import com.kafka.data.injection.Initializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * @author Vipul Kumar; dated 21/12/18.
 */
private typealias InitializerFunction = () -> @JvmSuppressWildcards Unit

@HiltAndroidApp
class KafkaApplication : Application() {

    @Inject
    internal fun init(@Initializers initializers: Set<@JvmSuppressWildcards InitializerFunction>) {
        initializers.forEach { it() }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when (level) {
            TRIM_MEMORY_MODERATE,
            TRIM_MEMORY_RUNNING_LOW,
            TRIM_MEMORY_RUNNING_MODERATE,
            TRIM_MEMORY_BACKGROUND,
            TRIM_MEMORY_UI_HIDDEN,
            TRIM_MEMORY_COMPLETE,
            TRIM_MEMORY_RUNNING_CRITICAL -> debug { "onTrimMemory $level" }
        }
    }
}
