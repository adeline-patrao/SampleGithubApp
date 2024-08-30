package com.scotiabank.domain

import com.scotiabank.domain.model.UserRepo
import com.scotiabank.domain.repository.GithubUserRepository
import com.scotiabank.domain.usecase.GetUserRepoUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserRepoUseCaseTest {

    private val repository = mockk<GithubUserRepository>()

    private lateinit var getUserRepoUseCase: GetUserRepoUseCase

    @Before
    fun setUp() {
        getUserRepoUseCase = GetUserRepoUseCase(repository)
    }

    @Test
    fun `test function call syntax`() = runTest {
        // Given
        val userRepo = UserRepo(userName = "123")
        every { repository.getUserRepo("123") } returns flowOf(userRepo)

        // When
        val result = getUserRepoUseCase("123")

        // Then
        result.collect { repo ->
            assert(repo == userRepo)
        }
    }

    @Test
    fun `test extension property invocation`() = runBlockingTest {
        // Given
        val userRepo = UserRepo(userName = "123")
        every { repository.getUserRepo("123") } returns flowOf(userRepo)

        // When
        val result = getUserRepoUseCase.invoke("123")

        // Then
        result.collect { repo ->
            assert(repo == userRepo)
        }
    }
}