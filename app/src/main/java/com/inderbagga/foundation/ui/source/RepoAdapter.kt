package com.inderbagga.foundation.ui.source

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.inderbagga.foundation.data.model.Repo
import com.inderbagga.foundation.databinding.MainFragmentListItemBinding

/**
 * Created by Inder Bagga on 21/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
class RepoAdapter(private val listener: RepoListAdapterInteraction): PagedListAdapter<Repo, RepoAdapter.RepoViewHolder>(
    usersDiffCallback
) {

    lateinit var context: Context

    interface RepoListAdapterInteraction {
        fun onRepoItemClick(repoItem: Repo)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {

        val repoItem = getItem(position)

          repoItem?.let {
            holder.bind(it)
        }
    }

    inner class RepoViewHolder(val binding: MainFragmentListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repoItem: Repo) {

            binding.repoItemLayout.setOnClickListener {
                listener.onRepoItemClick(repoItem)
            }

            binding.repo = repoItem
            //binding.ownerAvatarUrl=repoItem.owner.avatarUrl
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        context = parent.context

        val inflater = LayoutInflater.from(parent.context)
        val binding = MainFragmentListItemBinding.inflate(inflater)

        return RepoViewHolder(binding)
    }

    companion object {
        val usersDiffCallback = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }
        }
    }
}