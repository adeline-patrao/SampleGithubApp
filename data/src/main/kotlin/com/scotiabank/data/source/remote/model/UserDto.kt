package com.scotiabank.data.source.remote.model

import com.scotiabank.domain.model.User

data class UserDto(val name: String? = null, val avatar_url: String? = null) {
    fun toUser() = User(
        name = name,
        avatarUrl = avatar_url,
    )
}
