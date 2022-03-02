package com.example.candyspace.data.dto.response

import com.example.candyspace.data.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class ItemResponse(
    @Json(name = "badge_counts") val badgeCounts: BadgeCountsResponse? = null,
    @Json(name = "reputation") val reputation: Int? = null,
    @Json(name = "creation_date") val creationDate: Long? = null,
    @Json(name = "user_id") val userId: Int,
    @Json(name = "location") val location: String? = null,
    @Json(name = "profile_image") val profileImage: String? = null,
    @Json(name = "display_name") val displayName: String? = null
) {
    fun mapToUser(): User {
        val creationDateObject = if (creationDate != null) Date(creationDate * 1000) else null

        return User(
            userId.toString(),
            displayName,
            badgeCounts?.bronze ?: 0,
            badgeCounts?.silver ?: 0,
            badgeCounts?.gold ?: 0,
            reputation,
            profileImage,
            creationDateObject,
            location
        )
    }

}