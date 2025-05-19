package com.example.moneyforward.repository

import com.example.moneyforward.data.GitHubUser
import com.example.moneyforward.data.GitHubList
import com.example.moneyforward.data.GitHubRepos
import com.example.moneyforward.network.GitHubApi

class GitHubRepository(private val api: GitHubApi) {
    suspend fun getUser(username: String): GitHubUser {
        return api.getUser(username)
    }
    suspend fun getListUser(keyWords: String): GitHubList {
        return api.searchUsers(keyWords)
    }
    suspend fun getListUserReposNonForked(username: String): List<GitHubRepos> {
        val repos = api.getUserRepos(username)
        return repos.filter { !it.fork }
    }
}