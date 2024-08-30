package com.scotiabank.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRepo(
    val userName: String? = null,
    val avatarUrl: String? = null,
    val repos: List<Repo>? = emptyList(),
) : Parcelable {
    val starBadgeEnabled: Boolean
        get() {
            return (repos?.sumOf { repo ->
                repo.forks ?: 0
            } ?: 0) >= 5000
        }
}

data class User(val name: String? = null, val avatarUrl: String? = null)

@Parcelize
data class Repo(
    val name: String? = null,
    val description: String? = null,
    val updatedAt: String? = null,
    val forks: Int? = 0
) : Parcelable
