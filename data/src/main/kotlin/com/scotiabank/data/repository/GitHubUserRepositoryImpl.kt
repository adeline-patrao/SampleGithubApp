package com.scotiabank.data.repository

import com.scotiabank.data.source.GithubUserDataSource
import com.scotiabank.domain.model.Repo
import com.scotiabank.domain.model.User
import com.scotiabank.domain.model.UserRepo
import com.scotiabank.domain.repository.GithubUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubUserRepositoryImpl @Inject constructor(private val githubUserDataSource: GithubUserDataSource) :
    GithubUserRepository {

    override fun getUserRepo(userId: String): Flow<UserRepo> =
        getUser(userId).combine(getRepo(userId)) { user, repo ->
            UserRepo(userName = user.name, avatarUrl = user.avatarUrl, repos = repo)
        }

    private fun getUser(userId: String): Flow<User> = flow {
        emit(githubUserDataSource.fetchUser(userId).getOrElse {
            error("User not found! Please retry with a different user id.")
        }.toUser())
    }

    private fun getRepo(userId: String): Flow<List<Repo>> = flow {
        emit(githubUserDataSource.fetchUserRepos(userId).getOrElse {
            error("User not found! Please retry with a different user id")
        }.map { it.toRepo() })
    }
}