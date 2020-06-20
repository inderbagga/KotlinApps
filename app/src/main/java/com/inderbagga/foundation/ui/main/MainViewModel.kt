package com.inderbagga.foundation.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inderbagga.foundation.data.model.Repositories
import com.inderbagga.foundation.data.repo.GitRepo
import com.inderbagga.foundation.util.Result
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val gitRepo:GitRepo) : ViewModel() {

    val repositories = MutableLiveData<Result<Response<Repositories>>>()

    fun fetchRepositories(since: Int) {
        viewModelScope.launch {
            repositories.postValue(Result.loading(null))
            try {
                repositories.postValue(Result.success(gitRepo.getRepositories(since)))
            } catch (e: Exception) {
                repositories.postValue(Result.error(e.message.toString(), null))
            }
        }
    }
}
