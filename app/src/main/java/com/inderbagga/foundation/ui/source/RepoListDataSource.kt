package com.inderbagga.foundation.ui.source

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*
import androidx.lifecycle.MutableLiveData
import com.inderbagga.foundation.data.base.Status
import com.inderbagga.foundation.data.api.GitClientApi
import com.inderbagga.foundation.data.model.Repo
import com.inderbagga.foundation.data.model.Repos
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Inder Bagga on 21/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
class RepoListDataSource(private val gitApiClient: GitClientApi): PageKeyedDataSource<Int, Repo>() {

    private val dataSourceJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + dataSourceJob)
    val liveState: MutableLiveData<Status> = MutableLiveData()

    companion object{
        const val PAGE_SIZE = 100
    }

    override fun loadInitial(params: LoadInitialParams<Int>, initialCallback: LoadInitialCallback<Int, Repo>) {
        scope.launch {
            liveState.postValue(Status.LOADING)

            val data = gitApiClient.getRepositories(1)

            //Timber.tag("loadInitial").d(""+data?.message)

            when(data.status) {
                Status.ERROR -> liveState.postValue(Status.ERROR)
                Status.EMPTY -> liveState.postValue(Status.EMPTY)
                else -> {
                    data.response?.let { it ->

                        if(it?.isSuccessful!!){

                            processData(it,initialCallback,null)
                        }
                    }
                }
            }
        }
    }

    private fun processData(response: Response<Repos>, initialCallback: LoadInitialCallback<Int, Repo>?, paginatedCallback: LoadCallback<Int, Repo>?) {

        Timber.tag("headerLink").d(response.headers()["link"])

        val headerLink= response.headers()["link"].toString()
            .substringAfter("<https://api.github.com/repositories?since=")
            .substringBefore("\", <https://api.github.com/repositories")

        val pattern=Regex(">; rel=\"")

        val paginationData:List<String> = pattern.split(headerLink)

        response.body()?.let{

            if(paginationData[1] == "next"){

                if(initialCallback!=null){
                    initialCallback.onResult(it, null, paginationData[0].toInt())
                }else paginatedCallback?.onResult(it, paginationData[0].toInt())

                Timber.tag("pagination").d("since ${paginationData[0]}")

            }else{
                Timber.tag("pagination").d("since ${paginationData[1]}")
            }

            liveState.postValue(Status.SUCCESS)
        }

    }

    override fun loadAfter(params: LoadParams<Int>, paginatedCallback: LoadCallback<Int, Repo>) {
        scope.launch {
            val data = gitApiClient.getRepositories(params.key)
            data?.response.let { it ->

                if(it?.isSuccessful!!){
                    processData(it,null,paginatedCallback)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {

    }
}