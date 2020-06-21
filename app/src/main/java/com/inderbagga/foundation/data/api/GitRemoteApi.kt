package com.inderbagga.foundation.data.api

import com.inderbagga.foundation.data.model.Repos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Inder Bagga on 19/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
interface GitRemoteApi {

    @GET("repositories")
    suspend fun getRepositories(
        @Query("since")since:Int
    ): Response<Repos>
}