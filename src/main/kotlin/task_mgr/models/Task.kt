package task_mgr.models

import org.jetbrains.exposed.sql.*

data class Task(val id: Int, var name: String, val description: String, val label: String, val owner: String, val status: String, val dueDate: String)

object Tasks : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val description = varchar("description", 255)
    val label = varchar("label", 50)
    val owner = varchar("owner", 50)
    val status = varchar("status", 50)
    val dueDate = varchar("dueDate", 50)

    override val primaryKey = PrimaryKey(id)
}