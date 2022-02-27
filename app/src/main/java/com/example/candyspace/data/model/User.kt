package com.example.candyspace.data.model

import java.util.*

data class User(
    val id: String,
    val name: String? = null,
    val bronzeBadgeCounts: Int = 0,
    val silverBadgeCounts: Int = 0,
    val goldBadgeCounts: Int = 0,
    val reputation: Int? = null,
    val profileImage: String? = null,
    val creationDate: Date? = null,
    val location: String? = null,
)
