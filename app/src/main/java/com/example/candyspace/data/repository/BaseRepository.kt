package com.example.candyspace.data.repository

import android.content.Context
import com.example.candyspace.R
import retrofit2.Response

abstract class BaseRepository {
    protected fun <T> parseResponse(response: Response<T>, context: Context): Result<T?> {
        if (response.isSuccessful) {
            return Result.success(response.body())
        }

        var errorMessage = context.getString(R.string.unknown_error)
        when (response.code()) {
            in 400..499 -> {
                errorMessage = "${context.getString(R.string.client_error)} (Error ${response.code()})"
            }
            in 500..599 -> {
                errorMessage = "${context.getString(R.string.server_error)} (Error ${response.code()})"
            }
        }
        return Result.failure(Error(errorMessage))
    }
}