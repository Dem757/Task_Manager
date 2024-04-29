package com.task_mgr.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String? = null,
    val username: String,
    val password: String
)