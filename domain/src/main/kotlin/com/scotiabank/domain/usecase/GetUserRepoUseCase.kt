package com.scotiabank.domain.usecase

import com.scotiabank.domain.model.UserRepo
import com.scotiabank.domain.repository.GithubUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRepoUseCase @Inject constructor(private val repository: GithubUserRepository) {
    operator fun invoke(userId: String) : Flow<UserRepo> = repository.getUserRepo(userId)
}