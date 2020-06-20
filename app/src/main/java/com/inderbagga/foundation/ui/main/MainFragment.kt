package com.inderbagga.foundation.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inderbagga.foundation.R
import com.inderbagga.foundation.data.api.RetroBuilder
import com.inderbagga.foundation.data.repo.GitRepo
import com.inderbagga.foundation.util.Status
import timber.log.Timber

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var gitRepo: GitRepo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gitRepo = GitRepo(RetroBuilder.getClient())
        viewModel = getViewModel(gitRepo)

        viewModel.repositories.observe(requireActivity(), Observer { it ->

            when(it.status){

                Status.LOADING ->{

                    Log.d("repo","Api called!")
                }

                    Status.SUCCESS ->{

                    Log.d("repo",it?.response?.raw().toString())

                    if(it?.response?.isSuccessful!!){

                        Timber.tag("header Link").d(it?.response.headers().get("link"))

                        val headerLink=it?.response.headers().get("link").toString().substringAfter("<https://api.github.com/repositories?since=")
                            .substringBefore("\", <https://api.github.com/repositories")
                        val pattern=Regex(">; rel=\"")

                        val match:List<String> = pattern.split(headerLink)

                        if(match[1].equals("next")){
                            viewModel.fetchRepositories(match[0].toInt())
                            Timber.tag("Pagination").d("since ${match[0]}")
                        }else{
                            Timber.tag("Pagination").d("since ${match[1]}")
                        }

                        match.let {
                            for(match in it)
                                Timber.tag("match").d(match)
                        }

                        it?.response?.body()?.let{

                            val iterator = it.iterator()

                            for((index, repository) in iterator.withIndex()){
                                if(repository.description!=null){
                                    Timber.tag("repo at $index").d(repository.description)
                                }else{
                                    Timber.tag("repo at $index").e("description is not available!")
                                }
                            }
                        }
                    }
                }

                Status.ERROR ->{
                    it?.message?.let{
                        Log.e("repo",it)
                    }
                }
            }
        })

        viewModel.fetchRepositories(364)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun getViewModel(gitRepo: GitRepo): MainViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(gitRepo) as T
            }
        })[MainViewModel::class.java]
    }
}