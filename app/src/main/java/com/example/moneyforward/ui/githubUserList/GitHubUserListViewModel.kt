package com.example.moneyforward.ui.githubUserList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyforward.data.GitHubList
import com.example.moneyforward.domain.GetGitHubUserListUseCase
import kotlinx.coroutines.launch

class GitHubUserListViewModel(private val gitHubUserListUseCase: GetGitHubUserListUseCase) : ViewModel() {
    private val _userList = MutableLiveData<GitHubList>()
    val userList: LiveData<GitHubList> = _userList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val userName = MutableLiveData<String>().apply { value = "" }

    fun getGitHubUserList(keyWords: String) {
        viewModelScope.launch {
            val result = gitHubUserListUseCase(keyWords)
            result
                .onSuccess { _userList.postValue(it) }
                .onFailure {
                    _error.postValue("Failed: ${it.message}")
                }
        }
    }

    fun onButtonSearchClicked(keyWords: String) {
        getGitHubUserList(keyWords)
    }
}

