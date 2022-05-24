package org.kafka.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kafka.data.injection.ProcessLifetime
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseLogger @Inject constructor(
    @ApplicationContext private val context: Context,
    @ProcessLifetime private val scope: CoroutineScope,
    private val crashLogger: CrashLogger
) : Logger {
    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }
    private val userData = MutableStateFlow(UserData(""))

    init {
        scope.launch {
            updateUserProperty { userData.value }
            crashLogger.initialize(userData.value)
        }
    }

    override fun log(eventInfo: EventInfo) {
        val (eventName, map) = eventInfo
        firebaseAnalytics.logEvent(eventName, map.asBundle())
    }

    override fun updateUserProperty(update: UserData.() -> UserData) {
        val userData = update(userData.value)
    }

    override fun logScreenView(
        label: String,
        route: String?,
        arguments: Any?,
    ) {
        try {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                param(FirebaseAnalytics.Param.SCREEN_NAME, label)
                if (route != null) param("screen_route", route)

                // Expand out the rest of the parameters
                when {
                    arguments is Bundle -> {
                        for (key in arguments.keySet()) {
                            val value = arguments.get(key).toString()
                            // We don't want to include the label or route twice
                            if (value == label || value == route) continue

                            param("screen_arg_$key", value)
                        }
                    }
                    arguments != null -> param("screen_arg", arguments.toString())
                }
            }
        } catch (t: Throwable) {
            // Ignore, Firebase might not be setup for this project
        }
    }
}

@Singleton
class FirebaseCrashLogger @Inject constructor() : CrashLogger {
    override fun initialize(userData: UserData) {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        FirebaseCrashlytics.getInstance().setUserId(userData.userId)
    }

    override fun logFatal(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().log(throwable.message.orEmpty())
    }

    override fun logNonFatal(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }
}

fun Map<String, String>.asBundle() = Bundle().apply {
    entries.forEach {
        putString(it.key, it.value)
    }
}

typealias LogArgs = Map<String, Any?>?

fun FirebaseAnalytics.event(event: String, args: LogArgs = null) {
    Timber.d("Logging event: $event, $args")
    logEvent(
        event.replace(".", "_").lowercase(),
        Bundle().apply { args?.forEach { putString(it.key, it.value.toString()) } })
}

fun FirebaseAnalytics.click(event: String, args: LogArgs = null) = event("click.$event", args)

fun Context.event(event: String, args: LogArgs = null) =
    FirebaseAnalytics.getInstance(this).event(event, args)

fun Context.click(event: String, args: LogArgs = null) =
    FirebaseAnalytics.getInstance(this).click(event, args)
