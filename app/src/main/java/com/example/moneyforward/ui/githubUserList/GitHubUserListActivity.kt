package com.example.moneyforward.ui.githubUserList

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneyforward.R
import com.example.moneyforward.adapter.AdapterListUser
import com.example.moneyforward.data.GitHubUser
import com.example.moneyforward.databinding.ActivityGithubUserListBinding
import com.example.moneyforward.ui.githubUserDetail.GitHubUserDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitHubUserListActivity : AppCompatActivity() {
    private val vm: GitHubUserListViewModel by viewModel()
    private lateinit var binding: ActivityGithubUserListBinding
    private var listAdapterUser: AdapterListUser? = null

    companion object {
        const val TAG = "GitHubUserListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = setContentView(this, R.layout.activity_github_user_list)
        with(binding) {
            lifecycleOwner = this@GitHubUserListActivity
            viewModel = vm
        }

        with(vm) {
            userList.observe(this@GitHubUserListActivity) { userList ->
                listAdapterUser?.let {
                    it.notifyList(userList.items as ArrayList<GitHubUser>)
                }
            }
        }

        binding.buttonSearch.setOnClickListener {
            vm.onButtonSearchClicked(binding.tvUsername.text.toString())
        }

        setuprecyclerView()
    }

    private fun setuprecyclerView() {
        listAdapterUser = AdapterListUser(
            applicationContext,
            action = { name ->
                val intent = Intent(this, GitHubUserDetailActivity::class.java)
                intent.putExtra(TAG, name)
                startActivity(intent)
            },
            vm
        )

        binding.recyclerViewUsers.apply {
            adapter = listAdapterUser
            layoutManager = LinearLayoutManager(context)
        }
    }
}

