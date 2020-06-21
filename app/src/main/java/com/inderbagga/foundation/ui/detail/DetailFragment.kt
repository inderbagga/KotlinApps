package com.inderbagga.foundation.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.inderbagga.foundation.R
import com.inderbagga.foundation.data.model.Repo

import com.inderbagga.foundation.databinding.DetailFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Inder Bagga on 21/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DetailFragmentBinding =  DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)

        val repoItem: Repo = arguments?.getParcelable("repo")!!

      /*  arguments?.let {
            //DetailFragmentArgs.fromBundle(it) .owner
            val owner = requireArguments().getParcelable("owner")
        }*/

        binding.viewModel = detailViewModel
        binding.repo=repoItem

        detailViewModel.setup()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailViewModel.pageUrl.observe(viewLifecycleOwner, Observer {
            openInBrowser(it)
        })
    }

    private fun openInBrowser(pageUrl: String?) {
        pageUrl?.let {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        }
    }
}
