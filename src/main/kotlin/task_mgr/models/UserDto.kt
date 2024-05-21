package task_mgr.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int? = null,
    val username: String,
    val password: String
)