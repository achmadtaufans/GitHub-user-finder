package com.example.moneyforward.network

import okhttp3.Interceptor
import okhttp3.Response

class GitHubAuthInterceptor(private val tokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            tokenProvider()?.let {
                header("Authorization", "Bearer $it")
            }
            header("Accept", "application/vnd.github.v3+json")
        }.build()
        return chain.proceed(request)
    }
}