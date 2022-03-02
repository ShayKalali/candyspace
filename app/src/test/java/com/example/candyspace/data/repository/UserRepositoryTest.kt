package com.example.candyspace.data.repository

import android.content.Context
import com.example.candyspace.data.dto.response.*
import com.example.candyspace.network.ApiClient
import com.example.candyspace.network.UserApiService
import com.nhaarman.mockitokotlin2.any
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.*

class UserRepositoryTest {
    private lateinit var apiClient: ApiClient
    private lateinit var userApiService: UserApiService
    private lateinit var repository: UserRepository
    private lateinit var context: Context

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
        displayName = "Dummy User 2",
        location = "Paris",
        userId = 600
    )

    private val userResponse = UserResponse(
        items = mutableListOf(item1, item2),
        hasMore = false,
        quotaMax = 0,
        quotaRemaining = 0
    )

    private val topTagsResponse1 = TopTagsResponse(
        tagName = "Python"
    )
    private val topTagsResponse2 = TopTagsResponse(
        tagName = "Kotlin"
    )

    private val userTopTagsResponse = UserTopTagsResponse(
        items = mutableListOf(topTagsResponse1, topTagsResponse2),
        hasMore = false,
        quotaMax = 0,
        quotaRemaining = 0
    )

    @Before
    fun setUp() {
        apiClient = mockkClass(ApiClient::class)
        userApiService = mockkClass(UserApiService::class)
        context = mockkClass(Context::class)
        every { apiClient.getClient<UserApiService>() } returns userApiService
        repository = UserRepository(apiClient, context)
    }

    @Test
    fun getUsers_shouldReturnUsers() {
        val usersResponse = Response.success(userResponse)
        every {
            runBlocking {
                userApiService.getUsers(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            }
        } returns usersResponse
        val result = runBlocking { repository.getUsers() }

        Assert.assertTrue("Should be successful", result.isSuccess)

        val actualUsers = result.getOrNull()
        assertEquals("Should return 2 users", 2, actualUsers?.size)

        val actualUser1 = actualUsers!![0]
        assertEquals("Name should match", item1.displayName, actualUser1.name)
        assertEquals("ID should match", item1.userId.toString(), actualUser1.id)
        val actualUser2 = actualUsers[1]
        assertEquals("Name should match", item2.displayName, actualUser2.name)
        assertEquals("ID should match", item2.userId.toString(), actualUser2.id)
    }

    @Test
    fun getUsers_whenByName_thenShouldFilterUsersByName() {
        every {
            runBlocking {
                userApiService.getUsers(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            }
        } answers {
            val inName = args[5] as String?

            val result = UserResponse(
                items = userResponse.items.filter {
                    it.displayName!!.contains(
                        inName!!,
                        ignoreCase = true
                    )
                },
                hasMore = false,
                quotaRemaining = 0,
                quotaMax = 0
            )
            Response.success(result)
        }

        val result = runBlocking { repository.getUsers("User 2") }

        Assert.assertTrue("Should be successful", result.isSuccess)

        val actualUsers = result.getOrNull()
        assertEquals("Should return 1 users", 1, actualUsers?.size)

        val actualUser1 = actualUsers!![0]
        assertEquals("Name should match", item2.displayName, actualUser1.name)
        assertEquals("ID should match", item2.userId.toString(), actualUser1.id)
    }

    @Test
    fun getUsers_whenRequestIsInvalid_thenShouldReturnBadRequestError() {
        every {
            runBlocking {
                userApiService.getUsers(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            }
        } answers {
            Response.error(400, "".toResponseBody())
        }

        every { context.getString(any()) } returns "Error details"

        val result = runBlocking { repository.getUsers() }

        Assert.assertTrue("Should fail", result.isFailure)
        assertEquals(
            "Error should match",
            "Error details (Error 400)",
            result.exceptionOrNull()?.message
        )
    }

    @Test
    fun getUsers_whenServerThrottled_thenShouldReturnBadGatewayError() {
        every {
            runBlocking {
                userApiService.getUsers(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            }
        } answers {
            Response.error(502, "".toResponseBody())
        }

        every { context.getString(any()) } returns "Error details"

        val result = runBlocking { repository.getUsers() }

        Assert.assertTrue("Should fail", result.isFailure)
        assertEquals(
            "Error should match",
            "Error details (Error 502)",
            result.exceptionOrNull()?.message
        )
    }

    @Test
    fun getUsers_whenServerError_thenShouldReturnServerError() {
        every {
            runBlocking {
                userApiService.getUsers(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            }
        } answers {
            Response.error(500, "".toResponseBody())
        }

        every { context.getString(any()) } returns "Error details"

        val result = runBlocking { repository.getUsers() }

        Assert.assertTrue("Should fail", result.isFailure)
        assertEquals(
            "Error should match",
            "Error details (Error 500)",
            result.exceptionOrNull()?.message
        )
    }

    @Test
    fun getUsers_whenErrorIsUnknown_thenShouldReturnUnknownError() {
        every {
            runBlocking {
                userApiService.getUsers(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            }
        } answers {
            Response.error(600, "".toResponseBody())
        }

        every { context.getString(any()) } returns "Unknown error"

        val result = runBlocking { repository.getUsers() }

        Assert.assertTrue("Should fail", result.isFailure)
        assertEquals(
            "Error should match",
            "Unknown error",
            result.exceptionOrNull()?.message
        )
    }

    @Test
    fun getUserTopTags_shouldReturnUsersTags() {
        val usersTopTagsResponse = Response.success(userTopTagsResponse)
        every {
            runBlocking {
                userApiService.getUserTopTags(
                    any(),
                    any()
                )
            }
        } returns usersTopTagsResponse
        val result = runBlocking { repository.getUserTopTags(any()) }

        Assert.assertTrue("Should be successful", result.isSuccess)

        val actualUserTags = result.getOrNull()
        assertEquals("Should return 2 tags", 2, actualUserTags?.size)

        val actualUserTag1 = actualUserTags!![0]
        assertEquals("Tag name should match", topTagsResponse1.tagName, actualUserTag1)
        val actualUserTag2 = actualUserTags[1]
        assertEquals("Tag name should match", topTagsResponse2.tagName, actualUserTag2)
    }

    @Test
    fun getUserTopTags_whenRequestIsInvalid_thenShouldReturnBadRequestError() {
        every {
            runBlocking {
                userApiService.getUserTopTags(
                    any(),
                    any(),
                )
            }
        } answers {
            Response.error(400, "".toResponseBody())
        }

        every { context.getString(any()) } returns "Error details"

        val result = runBlocking { repository.getUserTopTags(any()) }

        Assert.assertTrue("Should fail", result.isFailure)
        assertEquals(
            "Error should match",
            "Error details (Error 400)",
            result.exceptionOrNull()?.message
        )
    }

    @Test
    fun getUserTopTags_whenServerThrottled_thenShouldReturnBadGatewayError() {
        every {
            runBlocking {
                userApiService.getUserTopTags(
                    any(),
                    any(),
                )
            }
        } answers {
            Response.error(502, "".toResponseBody())
        }

        every { context.getString(any()) } returns "Error details"

        val result = runBlocking { repository.getUserTopTags(any()) }

        Assert.assertTrue("Should fail", result.isFailure)
        assertEquals(
            "Error should match",
            "Error details (Error 502)",
            result.exceptionOrNull()?.message
        )
    }

    @Test
    fun getUserTopTags_whenServerError_thenShouldReturnServerError() {
        every {
            runBlocking {
                userApiService.getUserTopTags(
                    any(),
                    any(),
                )
            }
        } answers {
            Response.error(500, "".toResponseBody())
        }

        every { context.getString(any()) } returns "Error details"

        val result = runBlocking { repository.getUserTopTags(any()) }

        Assert.assertTrue("Should fail", result.isFailure)
        assertEquals(
            "Error should match",
            "Error details (Error 500)",
            result.exceptionOrNull()?.message
        )
    }

    @Test
    fun getUserTopTags_whenErrorIsUnknown_thenShouldReturnUnknownError() {
        every {
            runBlocking {
                userApiService.getUserTopTags(
                    any(),
                    any(),
                )
            }
        } answers {
            Response.error(600, "".toResponseBody())
        }

        every { context.getString(any()) } returns "Unknown error"

        val result = runBlocking { repository.getUserTopTags(any()) }

        Assert.assertTrue("Should fail", result.isFailure)
        assertEquals(
            "Error should match",
            "Unknown error",
            result.exceptionOrNull()?.message
        )
    }
}