package com.inderbagga.foundation.data.api

import com.inderbagga.foundation.data.model.Repositories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitApi {

    @GET("repositories")
    suspend fun getRepositories(
        @Query("since")since:Int
    ): Response<Repositories>
}