package com.scotiabank.domain.repository

import com.scotiabank.domain.model.UserRepo
import kotlinx.coroutines.flow.Flow

interface GithubUserRepository {
    fun getUserRepo(userId: String): Flow<UserRepo>
}