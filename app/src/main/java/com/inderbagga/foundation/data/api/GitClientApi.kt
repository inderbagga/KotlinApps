package com.inderbagga.foundation.data.api

import com.inderbagga.foundation.data.base.Result
import com.inderbagga.foundation.data.model.Repos
import retrofit2.Response

/**
 * Created by Inder Bagga on 19/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
interface GitClientApi {

    suspend fun getRepositories(since: Int): Result<Response<Repos>>
}