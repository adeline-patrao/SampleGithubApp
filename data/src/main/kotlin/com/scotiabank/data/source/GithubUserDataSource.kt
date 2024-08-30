package com.scotiabank.data.source

import com.scotiabank.data.source.remote.model.UserDto
import com.scotiabank.data.source.remote.model.UserRepoDto

interface GithubUserDataSource {
    suspend fun fetchUser(userId: String): Result<UserDto>
    suspend fun fetchUserRepos(userId: String): Result<List<UserRepoDto>>
}