package com.example.moneyforward.domain

import com.example.moneyforward.data.GitHubList
import com.example.moneyforward.repository.GitHubRepository

class GetGitHubUserListUseCase(
    private val repository: GitHubRepository
) {
    suspend operator fun invoke(keyWords: String): Result<GitHubList> {
        return try {
            val userList = repository.getListUser(keyWords)
            Result.success(userList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}