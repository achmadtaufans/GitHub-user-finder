package com.example.moneyforward.di

import android.content.Context
import com.example.moneyforward.BuildConfig
import com.example.moneyforward.domain.GetGitHubUserListUseCase
import com.example.moneyforward.domain.GetGitHubUserUseCase
import com.example.moneyforward.repository.GitHubRepository
import com.example.moneyforward.network.GitHubApi
import com.example.moneyforward.network.GitHubAuthInterceptor
import com.example.moneyforward.ui.githubUserDetail.GitHubUserDetailViewModel
import com.example.moneyforward.ui.githubUserList.GitHubUserListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val viewModelModule = module {
    viewModel { GitHubUserListViewModel(get()) }
    viewModel { GitHubUserDetailViewModel(get()) }
}

val apiModule = module {
    single {
        GitHubAuthInterceptor {
            BuildConfig.GIT_HUB_PERSONAL_TOKEN
        }
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<GitHubAuthInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<GitHubApi> {
        get<Retrofit>().create(GitHubApi::class.java)
    }
}

val repositoryModule = module {
    single { GitHubRepository(get()) }
}

val useCaseModule = module {
    factory { GetGitHubUserUseCase(get()) }
    factory { GetGitHubUserListUseCase(get()) }
}

var appModule = mutableListOf(viewModelModule, apiModule, repositoryModule, useCaseModule)

lateinit var koin: Koin

fun initDependencyInjection(ctx: Context) {
    koin = startKoin {
        androidContext(ctx)
    }.koin
}

inline fun <reified T : Any> inject() = lazy { get<T>() }
inline fun <reified T : Any> get(): T = koin.get()

