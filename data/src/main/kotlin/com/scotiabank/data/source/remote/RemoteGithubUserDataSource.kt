package com.scotiabank.data.source.remote

import android.util.Log
import com.scotiabank.data.source.GithubUserDataSource
import com.scotiabank.data.source.remote.model.UserDto
import com.scotiabank.data.source.remote.model.UserRepoDto
import retrofit2.Response
import javax.inject.Inject

class RemoteGithubUserDataSource @Inject constructor(
    private val githubApi: GithubApi
) :
    GithubUserDataSource {
    override suspend fun fetchUser(userId: String): Result<UserDto> {
        return try {
            githubApi.getUser(userId).toResult()
        } catch (e: Exception) {
            Result.failure(IllegalStateException("Exception from retrofit ${e.message}.", e))
        }
    }

    override suspend fun fetchUserRepos(userId: String): Result<List<UserRepoDto>> {
        return try {
            githubApi.getUserRepos(userId).toResult()
        } catch (e: Exception) {
            Result.failure(IllegalStateException("Exception from retrofit ${e.message}.", e))
        }
    }

    private fun <T> Response<T>.toResult(): Result<T> {
        val code = this.code()
        if (this.isSuccessful) {
            Log.d("Adeline", "getUserRepo network datasource success [$code].")
            return Result.success(body()!!)
        } else {
            Log.d("Adeline", "getUserRepo() network datasource error [$code], ${errorBody()?.source().toString()}.")
            return Result.failure(IllegalStateException(errorBody()?.source().toString()))
        }
    }
}