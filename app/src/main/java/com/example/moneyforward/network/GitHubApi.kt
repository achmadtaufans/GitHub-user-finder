package com.example.moneyforward.network

import com.example.moneyforward.data.GitHubUser
import com.example.moneyforward.data.GitHubList
import com.example.moneyforward.data.GitHubRepos
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") keyword: String): GitHubList

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): GitHubUser

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String,
        @Query("per_page") perPage: Int = 100
    ): List<GitHubRepos>
}