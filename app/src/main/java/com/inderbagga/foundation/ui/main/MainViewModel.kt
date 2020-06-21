package com.inderbagga.foundation.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.inderbagga.foundation.data.model.Repo
import com.inderbagga.foundation.ui.source.RepoListDataSource
import com.inderbagga.foundation.ui.source.RepoListDataSourceFactory
import java.util.concurrent.Executors

/**
 * Created by Inder Bagga on 19/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
class MainViewModel(private val repoListDataSourceFactory: RepoListDataSourceFactory) : ViewModel() {

    var dataSource : MutableLiveData<RepoListDataSource>
    lateinit var liveRepo: LiveData<PagedList<Repo>>
    val isLoading: ObservableField<Boolean> = ObservableField()
    var errorMessage: ObservableField<String> = ObservableField()

    init {
        isLoading.set(true)
        errorMessage.set(null)
        dataSource = repoListDataSourceFactory.repoSource
        initRepoListFactory()
    }

    private fun initRepoListFactory() {

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(RepoListDataSource.PAGE_SIZE)
            .setPageSize(RepoListDataSource.PAGE_SIZE)
            .setPrefetchDistance(10)
            .build()

        val executor = Executors.newFixedThreadPool(5)

        liveRepo = LivePagedListBuilder<Int, Repo>(repoListDataSourceFactory, config)
            .setFetchExecutor(executor)
            .build()
    }
}
