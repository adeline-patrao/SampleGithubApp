package com.scotiabank.data.source.remote.model

import com.scotiabank.domain.model.Repo

data class UserRepoDto(
    val name: String? = null,
    val description: String? = null,
    val updated_at: String? = null,
    val forks: Int? = 0
) {
    fun toRepo(): Repo {
        return Repo(
            name = name,
            description = description,
            updatedAt = updated_at,
            forks = forks
        )
    }
}
