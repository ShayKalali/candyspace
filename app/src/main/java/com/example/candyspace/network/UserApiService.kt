package com.example.candyspace.network


import com.example.candyspace.data.dto.response.UserResponse
import com.example.candyspace.data.dto.response.UserTopTagsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("site") site: String,
        @Query("inname") inName: String?,
    ): Response<UserResponse>

    @GET("users/{ids}/top-tags")
    suspend fun getUserTopTags(
        @Path("ids") ids :String,
        @Query("site") site: String,
    ): Response<UserTopTagsResponse>

}