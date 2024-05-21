// User.kt
package task_mgr.models

import org.jetbrains.exposed.sql.*

data class User(val id: Int, val username: String, val password: String)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 250)
    val password = varchar("password", 500)

    override val primaryKey = PrimaryKey(id)
}