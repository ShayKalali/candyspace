package com.example.candyspace.data.dto.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopTagsResponse (


    @Json(name = "tag_name")
    val tagName: String
)