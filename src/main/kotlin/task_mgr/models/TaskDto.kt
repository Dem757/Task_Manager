package task_mgr.models

import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id: Int? = null,
    val name: String,
    val description: String,
    val label: String,
    val owner: String,
    val status: String,
    val dueDate: String
)