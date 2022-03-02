package com.example.candyspace.data.dto.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BadgeCountsResponse(

    @Json(name = "bronze") val bronze: Int? = null,
    @Json(name = "silver") val silver: Int? = null,
    @Json(name = "gold") val gold: Int? = null
)