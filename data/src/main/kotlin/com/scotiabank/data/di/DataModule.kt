package com.scotiabank.data.di

import com.scotiabank.data.repository.GitHubUserRepositoryImpl
import com.scotiabank.data.source.GithubUserDataSource
import com.scotiabank.data.source.remote.GithubApi
import com.scotiabank.data.source.remote.RemoteGithubUserDataSource
import com.scotiabank.domain.repository.GithubUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideGithubApi(
        retrofit: Retrofit
    ): GithubApi = retrofit.create(GithubApi::class.java)

    @Provides
    @Singleton
    fun provideGitRepository(remoteGithubUserDataSource: RemoteGithubUserDataSource): GithubUserRepository {
        return GitHubUserRepositoryImpl(remoteGithubUserDataSource)
    }

    @Provides
    @Singleton
    fun provideNetworkGithubDataSource(api: GithubApi): GithubUserDataSource {
        return RemoteGithubUserDataSource(api)
    }
}