package com.example.moneyforward.data

data class GitHubRepos(
    val name: String,
    val language: String? = null,
    val stargazers_count: Int,
    val description: String? = null,
    val fork: Boolean
)