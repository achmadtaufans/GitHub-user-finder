package com.example.moneyforward.data

data class GitHubList(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<GitHubUser>
)