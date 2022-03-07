package com.example.candyspace.data.repository

import android.content.Context
import com.example.candyspace.data.model.User
import com.example.candyspace.di.qualifier.ApplicationContext
import com.example.candyspace.network.UserApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService,
    @ApplicationContext val context: Context
) : BaseRepository() {

    suspend fun getUsers(name: String? = null): Result<List<User>> =
        withContext(Dispatchers.IO) {
            val response = userApiService
                .getUsers(1, 20, "desc", "name", "stackoverflow", name)
            val result = parseResponse(response, context)

            if (result.isSuccess) {
                return@withContext Result.success(
                    result.getOrNull()?.mapToListOfUsers() ?: emptyList()
                )
            }

            return@withContext Result.failure(result.exceptionOrNull()!!)
        }

    suspend fun getUserTopTags(userId: String): Result<List<String>> =
        withContext(Dispatchers.IO) {
            val response = userApiService
                .getUserTopTags(userId, "stackoverflow")
            val result = parseResponse(response, context)

            if (result.isSuccess) {
                return@withContext Result.success(
                    result.getOrNull()?.mapToListOfTopTags() ?: emptyList()
                )
            }

            return@withContext Result.failure(result.exceptionOrNull()!!)
        }
}