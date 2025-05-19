package com.example.moneyforward.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moneyforward.R
import com.example.moneyforward.data.GitHubRepos
import com.example.moneyforward.data.GitHubUser
import com.example.moneyforward.databinding.RecyclerGithubRepoItemBinding
import com.example.moneyforward.databinding.RecyclerGithubUserItemBinding
import com.example.moneyforward.ui.githubUserDetail.GitHubUserDetailViewModel
import com.example.moneyforward.ui.githubUserList.GitHubUserListViewModel
import com.example.moneyforward.util.GlideUtils

class AdapterListRepo(val action: (String) -> Unit = {},
                      val viewModel: GitHubUserDetailViewModel
) : RecyclerView.Adapter<AdapterListRepo.Holder>() {

                          private var itemList: MutableList<GitHubRepos> = arrayListOf()
    inner class Holder(var binding: RecyclerGithubRepoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemLayoutView = LayoutInflater.from(parent.context)
        return Holder(RecyclerGithubRepoItemBinding.inflate(itemLayoutView, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyList(newData : ArrayList<GitHubRepos> = ArrayList()) {
        val diffCallback = DiffCallback(this.itemList, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.itemList.clear()
        this.itemList.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val itemAtPos = itemList[position]
        holder.binding.vm = viewModel
        holder.binding.apply {
            tvNameRepoValue.text = itemAtPos.name
            tvDevelopmentLanguageValue.text = itemAtPos.language
            tvStarsValue.text = itemAtPos.stargazers_count.toString()
            tvDescriptionValue.text = itemAtPos.description
            clUserItem.setOnClickListener {
                action.invoke(itemAtPos.name)
            }
        }
    }

    inner class DiffCallback(
        private val oldList: List<GitHubRepos>,
        private val newList: List<GitHubRepos>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val wordOld = oldList[oldPosition]
            val wordNew = newList[newPosition]

            return wordOld == wordNew
        }

        @Nullable
        override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
            return super.getChangePayload(oldPosition, newPosition)
        }
    }
}