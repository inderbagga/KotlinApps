package com.inderbagga.foundation.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object RetroBuilder {

    private const val API_BASE_URL = "https://api.github.com/"

   private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("GithubAPI").i(message)
            }
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(10,TimeUnit.SECONDS)
        .connectTimeout(10,TimeUnit.SECONDS)
        .addInterceptor(provideHttpLoggingInterceptor())
        .build()

    fun getClient(): GitApi {

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitApi::class.java)
    }
}