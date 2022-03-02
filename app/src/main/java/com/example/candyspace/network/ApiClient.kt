package com.example.candyspace.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val BASE_URL = "https://api.stackexchange.com/2.3/"

open class ApiClient @Inject constructor(
) {
    private val requestTimeout: Long = 60

    private val moshiBuilder = Moshi.Builder()
    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null

    private var previousUrl: String? = null

    fun getRetrofit(url: String): Retrofit? {
        if (okHttpClient == null) initOkHttp()
        if (retrofit == null || (previousUrl != null && previousUrl != url)) {

            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient!!)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    MoshiConverterFactory.create(moshiBuilder.build())
                )
                .build()
        }
        previousUrl = url
        return retrofit
    }

    inline fun <reified T : Any> getClient(url: String = BASE_URL): T {
        return getRetrofit(url)?.create(T::class.java)!!
    }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(requestTimeout, TimeUnit.SECONDS)
            .readTimeout(requestTimeout, TimeUnit.SECONDS)
            .writeTimeout(requestTimeout, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor {
            val original = it.request()
            val requestBuilder: Request.Builder = original.newBuilder()
            val request = requestBuilder.build()
            return@addInterceptor it.proceed(request)
        }

        okHttpClient = httpClient.build()
    }
}