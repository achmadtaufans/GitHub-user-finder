package com.example.moneyforward.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity

class RepoWebViewActivity : ComponentActivity() {
    companion object {
        const val REPO_URL = "repo_url"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }

        setContentView(webView)

        val repoUrl = intent.getStringExtra(REPO_URL)
        if (repoUrl != null) {
            webView.loadUrl(repoUrl)
        }
    }
}