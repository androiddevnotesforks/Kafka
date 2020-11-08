package com.kafka.data.model.model

import com.kafka.data.extensions.e
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

interface Callback<T> {
    fun onResult(data: T?, exception: Exception? = null)
}

/**
 * Converts a callback into a [suspendCancellableCoroutine]
 * */
suspend fun <T> awaitCallback(block: (Callback<T>) -> Unit): T =
    suspendCancellableCoroutine { cont ->
        block(object : Callback<T> {
            override fun onResult(data: T?, exception: Exception?) {
                if (exception != null) {
                    cont.resumeWithException(exception)
                } else {
                    cont.resume(data!!) { com.kafka.data.extensions.e(it) { "awaitCallback error" } }
                }
            }
        })
    }
