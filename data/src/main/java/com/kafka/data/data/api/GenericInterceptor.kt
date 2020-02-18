package com.kafka.data.data.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by VipulKumar on 20/09/18.
 */
internal class GenericInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter("output", "json")
            .addQueryParameter("rows", "40")
            .addQueryParameter("page", "1")
            .addQueryParameter("sort", "-downloads")
            .build()
        request = request.newBuilder().url(url).method(request.method(), request.body()).build()
        return chain.proceed(request)
    }
}
