package com.kafka.data.model.item

import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "response")
    val response: Response,
    @Json(name = "responseHeader")
    val responseHeader: ResponseHeader
)
