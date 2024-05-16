package task_mgr.models

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class TaskDto(
    val id: String? = null,
    val name: String,
    val description: String,
    val label: String,
    val owner: String,
    val status: String,
    val dueDate: String
)