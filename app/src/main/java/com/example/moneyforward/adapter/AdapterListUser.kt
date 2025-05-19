package com.example.moneyforward.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyforward.data.GitHubUser
import com.example.moneyforward.databinding.RecyclerGithubUserItemBinding
import com.example.moneyforward.ui.githubUserList.GitHubUserListViewModel
import com.example.moneyforward.util.GlideUtils

class AdapterListUser(private var ctx: Context,
                      val action: (String) -> Unit = {},
                      val viewModel: GitHubUserListViewModel
) : RecyclerView.Adapter<AdapterListUser.Holder>() {

                          private var itemList: MutableList<GitHubUser> = arrayListOf()
    inner class Holder(var binding: RecyclerGithubUserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemLayoutView = LayoutInflater.from(parent.context)
        return Holder(RecyclerGithubUserItemBinding.inflate(itemLayoutView, parent, false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyList(newData : ArrayList<GitHubUser> = ArrayList()) {
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
            if (itemAtPos.avatar_url.isNotEmpty()) {
                GlideUtils.setupGlide(ctx, itemAtPos.avatar_url, imgPhoto)
            }
            tvNameUser.text = itemAtPos.login
            clUserItem.setOnClickListener {
                action.invoke(itemAtPos.login)
            }
        }
    }

    inner class DiffCallback(
        private val oldList: List<GitHubUser>,
        private val newList: List<GitHubUser>
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