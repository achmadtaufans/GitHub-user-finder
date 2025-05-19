package com.example.moneyforward.domain

import com.example.moneyforward.data.GitHubRepos
import com.example.moneyforward.data.GitHubUser
import com.example.moneyforward.repository.GitHubRepository

class GetGitHubUserUseCase(
    private val repository: GitHubRepository
) {
    suspend operator fun invoke(username: String): Result<Pair<GitHubUser, List<GitHubRepos>>> {
        return try {
            val user = repository.getUser(username)
            val repos = repository.getListUserReposNonForked(username)
            Result.success(Pair(user, repos))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}