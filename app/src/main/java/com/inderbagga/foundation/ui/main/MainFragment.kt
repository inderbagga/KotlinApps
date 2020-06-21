package com.inderbagga.foundation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inderbagga.foundation.R
import com.inderbagga.foundation.data.base.Status
import com.inderbagga.foundation.data.model.Repo
import com.inderbagga.foundation.databinding.MainFragmentBinding
import com.inderbagga.foundation.ui.source.RepoAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * Created by Inder Bagga on 19/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
class MainFragment : Fragment(), RepoAdapter.RepoListAdapterInteraction {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var repoViewer: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: MainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        binding.viewModel = mainViewModel

        repoViewer = binding.root.findViewById(R.id.repoViewer)

        initAdapterAndObserve()

        return binding.root
    }

    private fun initAdapterAndObserve() {
        val repoAdapter = RepoAdapter(this)

        repoViewer.layoutManager = LinearLayoutManager(context)
        repoViewer.adapter = repoAdapter

        Transformations.switchMap(mainViewModel.dataSource) { dataSource -> dataSource.liveState }
            .observe(viewLifecycleOwner, Observer {
                when(it) {
                    Status.LOADING -> {
                        mainViewModel.isLoading.set(true)
                        mainViewModel.errorMessage.set(null)
                    }
                    Status.SUCCESS -> {
                        mainViewModel.isLoading.set(false)
                        mainViewModel.errorMessage.set(null)
                    }
                    Status.EMPTY -> {
                        mainViewModel.isLoading.set(false)
                        mainViewModel.errorMessage.set(getString(R.string.msg_repo_list_is_empty))
                    }
                    else -> {
                        mainViewModel.isLoading.set(false)
                        mainViewModel.errorMessage.set(getString(R.string.msg_fetch_repo_list_has_error))
                    }
                }
            })

        mainViewModel.liveRepo.observe(viewLifecycleOwner, Observer {
            repoAdapter.submitList(it)
        })
    }

    override fun onRepoItemClick(repoItem: Repo) {

        var bundle = bundleOf("repo" to repoItem)
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)

        Timber.tag("onRepoItemClick").d(repoItem.name)

        /*val direction = MainFragmentDestinations.actionMainFragmentToDetailFragment(repoItem.owner)
        findNavController().navigate(direction)*/
    }
}