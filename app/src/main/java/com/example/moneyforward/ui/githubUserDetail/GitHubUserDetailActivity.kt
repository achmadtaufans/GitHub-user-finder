package com.example.moneyforward.ui.githubUserDetail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneyforward.BuildConfig
import com.example.moneyforward.R
import com.example.moneyforward.adapter.AdapterListRepo
import com.example.moneyforward.data.GitHubRepos
import com.example.moneyforward.data.GitHubUser
import com.example.moneyforward.databinding.ActivityGithubUserDetailBinding
import com.example.moneyforward.ui.RepoWebViewActivity
import com.example.moneyforward.ui.RepoWebViewActivity.Companion.REPO_URL
import com.example.moneyforward.ui.githubUserList.GitHubUserListActivity.Companion.TAG
import com.example.moneyforward.util.GlideUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitHubUserDetailActivity : AppCompatActivity() {
    private val vm: GitHubUserDetailViewModel by viewModel()
    private lateinit var binding: ActivityGithubUserDetailBinding
    private var listAdapterRepos: AdapterListRepo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = setContentView(this, R.layout.activity_github_user_detail)
        with(binding) {
            lifecycleOwner = this@GitHubUserDetailActivity
        }

        with(vm) {
            user.observe(this@GitHubUserDetailActivity) { user ->
                setupUI(user)
            }
            userRepoList.observe(this@GitHubUserDetailActivity) { userRepoList ->
                listAdapterRepos?.notifyList(userRepoList as? ArrayList<GitHubRepos> ?: arrayListOf())
            }
        }

        vm.fetchGitHubUser(intent.getStringExtra(TAG).toString())

        setupToolbar()
        setuprecyclerView()
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Personal Info"
    }

    private fun setupUI(user: GitHubUser) {
        GlideUtils.setupGlide(applicationContext, user.avatar_url, binding.imgPhoto)

        binding.tvLoginUser.text = user.login
        binding.tvNameUserValue.text = if (!user.name.isNullOrEmpty()) user.name else "-"
        binding.tvFollowingValue.text = user.following.toString()
        binding.tvFollowersValue.text = user.followers.toString()
    }

    private fun setuprecyclerView() {
        listAdapterRepos = AdapterListRepo(
            action = { name ->
                val intent = Intent(this, RepoWebViewActivity::class.java).apply {
                    putExtra(REPO_URL, BuildConfig.BASE_GITHUB_URL + "/${binding.tvLoginUser.text}/${name}")
                }
                startActivity(intent)
            },
            vm
        )

        binding.recyclerViewRepos.apply {
            adapter = listAdapterRepos
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

