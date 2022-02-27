package com.example.candyspace.data.dto.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserTopTagsResponse(

    val items: List<TopTagsResponse>,

    @Json(name = "has_more")
    val hasMore: Boolean,

    @Json(name = "quota_max")
    val quotaMax: Long,

    @Json(name = "quota_remaining")
    val quotaRemaining: Long
) {
    fun mapToListOfTopTags(): MutableList<String> =
        items.map { it.tagName }.toMutableList()
}