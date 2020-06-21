package com.inderbagga.foundation.data.repo

import com.inderbagga.foundation.data.api.GitClientApi
import com.inderbagga.foundation.data.api.GitRemoteApi
import com.inderbagga.foundation.data.base.Result
import com.inderbagga.foundation.data.model.Repos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by Inder Bagga on 20/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
class GitRepo(private val gitRemoteApi: GitRemoteApi) : GitClientApi {

    override suspend fun getRepositories(since: Int): Result<Response<Repos>> = withContext(Dispatchers.Main) {

        try {
            val data = gitRemoteApi.getRepositories(since)

            data?.let {
                Result.success(it)
            }
        } catch (ex: Throwable) {
            Result.error<Response<Repos>>("${ex.message}",null)
        }
    } as Result<Response<Repos>>
}
