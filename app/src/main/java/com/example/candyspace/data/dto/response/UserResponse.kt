package com.example.candyspace.data.dto.response

import com.example.candyspace.data.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(

    @Json(name = "items") val items: List<ItemResponse>,
    @Json(name = "has_more") val hasMore: Boolean,
    @Json(name = "quota_max") val quotaMax: Int,
    @Json(name = "quota_remaining") val quotaRemaining: Int
) {
    fun mapToListOfUsers(): MutableList<User> =
        items.map { it.mapToUser() }.toMutableList()
}