package task_mgr.dao

import task_mgr.dao.DatabaseSingleton.dbQuery
import task_mgr.models.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {
    private fun resultRowTask(row: ResultRow) = Task(
        id = row[Tasks.id],
        name = row[Tasks.name],
        description = row[Tasks.description],
        label = row[Tasks.label],
        owner = row[Tasks.owner],
        status = row[Tasks.status],
        dueDate = row[Tasks.dueDate]
    )

    override suspend fun addTask(Task): Task? = dbQuery {
        val insertStatement = Tasks.insert {
            it[Tasks.id] = id
            it[Tasks.name] = name
            it[Tasks.description] = description
            it[Tasks.label] = label
            it[Tasks.owner] = owner
            it[Tasks.status] = status
            it[Tasks.dueDate] = dueDate
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowTask)
    }

    override suspend fun getTask(id: Int): Task? = dbQuery {
        Tasks.select { Tasks.id eq id }
            .map(::resultRowTask)
            .singleOrNull()
    }

    override suspend fun getAllTasks(): List<Task> = dbQuery {
        Tasks.selectAll().map(::resultRowTask)
    }

    override suspend fun updateTask(task: Task): Boolean = dbQuery {
        Tasks.update({ Tasks.id eq task.id }) {
            it[Tasks.name] = task.name
            it[Tasks.description] = task.description
            it[Tasks.label] = task.label
            it[Tasks.owner] = task.owner
            it[Tasks.status] = task.status
            it[Tasks.dueDate] = task.dueDate
        } > 0
    }

    override suspend fun deleteTask(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    private fun resultRowUser(row: ResultRow) = User(
        id = row[Users.id],
        username = row[Users.username],
        password = row[Users.password]
    )

    override suspend fun addUser(id: Int, username: String, password: String): User? = dbQuery {
        val insertStatement = Users.insert {
            it[Users.id] = id
            it[Users.username] = username
            it[Users.password] = password
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowUser)
    }

    override suspend fun getUser(id: Int): User? = dbQuery {
        Users.select { Users.id eq id }
            .map(::resultRowUser)
            .singleOrNull()
    }

    override suspend fun getAllUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowUser)
    }

    override suspend fun updateUser(user: User): Boolean = dbQuery {
        Users.update({ Users.id eq user.id }) {
            it[Users.username] = user.username
            it[Users.password] = user.password
        } > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }
}