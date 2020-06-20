package com.inderbagga.foundation.data.repo

import com.inderbagga.foundation.data.api.GitApi

class GitRepo(private val gitApi: GitApi){

    suspend fun getRepositories(since: Int) =gitApi.getRepositories(since)
}