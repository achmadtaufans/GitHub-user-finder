package com.example.moneyforward.ui.githubUserDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyforward.data.GitHubRepos
import com.example.moneyforward.data.GitHubUser
import com.example.moneyforward.domain.GetGitHubUserUseCase
import kotlinx.coroutines.launch

class GitHubUserDetailViewModel(private val gitHubUserUseCase: GetGitHubUserUseCase) : ViewModel() {
    private val _user = MutableLiveData<GitHubUser>()
    val user: MutableLiveData<GitHubUser> = _user

    private val _userRepoList = MutableLiveData<List<GitHubRepos>>(emptyList())
    val userRepoList: MutableLiveData<List<GitHubRepos>> = _userRepoList

    private val _error = MutableLiveData<String>()
    val error: MutableLiveData<String> = _error

    fun fetchGitHubUser(username: String) {
        viewModelScope.launch {
            val result = gitHubUserUseCase(username)
            result
                .onSuccess {
                    _user.postValue(it.first)
                    _userRepoList.postValue(it.second)
                }
                .onFailure { _error.postValue("Failed: ${it.message}") }
        }
    }
}

