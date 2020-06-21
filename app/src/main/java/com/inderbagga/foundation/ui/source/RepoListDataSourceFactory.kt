package com.inderbagga.foundation.ui.source

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import com.inderbagga.foundation.data.api.GitClientApi
import com.inderbagga.foundation.data.model.Repo

/**
 * Created by Inder Bagga on 21/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
class RepoListDataSourceFactory(private val gitClientApi: GitClientApi): DataSource.Factory<Int, Repo>() {

    val repoSource: MutableLiveData<RepoListDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Repo> {
        val repoListDataSource = RepoListDataSource(gitClientApi)
        repoSource.postValue(repoListDataSource)
        return repoListDataSource
    }
}