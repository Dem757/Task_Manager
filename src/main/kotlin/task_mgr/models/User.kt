// User.kt
package task_mgr.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.jetbrains.exposed.sql.*


data class User(
    @BsonId
    val id: Id<User>? = null,
    val username: String,
    val password: String
)

//data class User(val id: Int, val username: String, val password: String)
//
//object Users : Table() {
//    val id = integer("id").autoIncrement()
//    val username = varchar("username", 50)
//    val password = varchar("password", 50)
//
//    override val primaryKey = PrimaryKey(id)
//}