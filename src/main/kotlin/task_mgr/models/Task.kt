package task_mgr.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import java.util.Date

data class Task(
    @BsonId
    val id: Id<Task>? = null,
    val name: String,
    val description: String,
    val label: String,
    val owner: String,
    val status: String,
    val dueDate: String
)