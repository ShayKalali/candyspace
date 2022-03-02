package com.example.candyspace.data.dto.response

import org.junit.Assert
import org.junit.Test
import java.util.*

class UserResponseTest {
    private val item1 = ItemResponse(
        reputation = 10,
        badgeCounts = BadgeCountsResponse(1, 2, 3),
        profileImage = "DummyProfileImageUrl1",
        creationDate = Date().time / 1000,
        displayName = "Dummy User 1",
        location = "London",
        userId = 500
    )

    private val item2 = ItemResponse(
        reputation = 10,
        badgeCounts = BadgeCountsResponse(4, 5, 6),
        profileImage = "DummyProfileImageUrl2",
        creationDate = (Date().time + 2000) / 1000,
        displayName = "Dummy User",
        location = "Paris",
        userId = 600
    )

    private val item3 = ItemResponse(
        reputation = 10,
        badgeCounts = BadgeCountsResponse(7, 8, 9),
        profileImage = "DummyProfileImageUrl3",
        creationDate = (Date().time + 60000) / 1000,
        displayName = "Dummy User 3",
        location = "Berlin",
        userId = 700
    )

    @Test
    fun mapToListOfUsers_thenShouldReturnOneUser() {
        val itemsResponse = mutableListOf(item1)
        val userResponse =
            UserResponse(
                items = itemsResponse,
                hasMore = false, quotaMax = 0,
                quotaRemaining = 0
            )

        val actualUsers = userResponse.mapToListOfUsers()
        Assert.assertEquals("There should be 1 user", 1, actualUsers.size)

        val actualUser = actualUsers[0]
        Assert.assertEquals("Name should be correct", item1.displayName, actualUser.name)
        Assert.assertEquals(
            "Profile image should be correct",
            item1.profileImage,
            actualUser.profileImage

        )
        Assert.assertEquals(
            "Bronze badge counts should be correct",
            item1.badgeCounts!!.bronze,
            actualUser.bronzeBadgeCounts
        )

        Assert.assertEquals(
            "Silver badge counts should be correct",
            item1.badgeCounts!!.silver,
            actualUser.silverBadgeCounts
        )
        Assert.assertEquals("Gold badge counts should be correct", 3, actualUser.goldBadgeCounts)
        Assert.assertEquals(
            "Creation date should be correct",
            Date(item1.creationDate!! * 1000),
            actualUser.creationDate
        )
        Assert.assertEquals("ID should be correct", item1.userId.toString(), actualUser.id)
        Assert.assertEquals("Location should be correct", item1.location, actualUser.location)
        Assert.assertEquals("Reputation should be correct", item1.reputation, actualUser.reputation)
    }


    @Test
    fun mapToListOfUsers_thenShouldReturnThreeUser() {
        val itemsResponse = mutableListOf(item1, item2, item3)
        val userResponse =
            UserResponse(
                items = itemsResponse,
                hasMore = false, quotaMax = 0,
                quotaRemaining = 0
            )

        val actualUsers = userResponse.mapToListOfUsers()
        Assert.assertEquals("There should be 3 user", 3, actualUsers.size)

        // User 1
        for (index in 1..1) {
            val actualUser = actualUsers[index]
            val item = userResponse.items[index]
            Assert.assertEquals("Name should be correct", item.displayName, actualUser.name)
            Assert.assertEquals(
                "Profile image should be correct",
                item.profileImage,
                actualUser.profileImage
            )
            Assert.assertEquals(
                "Bronze badge counts should be correct",
                item.badgeCounts!!.bronze,
                actualUser.bronzeBadgeCounts
            )
            Assert.assertEquals(
                "Silver badge counts should be correct",
                item.badgeCounts!!.silver,
                actualUser.silverBadgeCounts
            )
            Assert.assertEquals(
                "Gold badge counts should be correct",
                item.badgeCounts!!.gold,
                actualUser.goldBadgeCounts
            )
            Assert.assertEquals(
                "Creation date should be correct",
                Date(item.creationDate!! * 1000),
                actualUser.creationDate
            )
            Assert.assertEquals("ID should be correct", item.userId.toString(), actualUser.id)
            Assert.assertEquals("Location should be correct", item.location, actualUser.location)
            Assert.assertEquals(
                "Reputation should be correct",
                item.reputation,
                actualUser.reputation
            )
        }
    }
}