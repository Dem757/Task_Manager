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

    override suspend fun addTask(name: String, description: String, label: String, owner: String, status: String, dueDate:String): Task? = dbQuery {
        val insertStatement = Tasks.insert {
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
            it[name] = task.name
            it[description] = task.description
            it[label] = task.label
            it[owner] = task.owner
            it[status] = task.status
            it[dueDate] = task.dueDate
        } > 0
    }

    override suspend fun deleteTask(id: Int): Boolean = dbQuery {
        Tasks.deleteWhere { Tasks.id eq id } > 0
    }

    private fun resultRowUser(row: ResultRow) = User(
        id = row[Users.id],
        username = row[Users.username],
        password = row[Users.password]
    )

    override suspend fun addUser(username: String, password: String): User? = dbQuery {
        val insertStatement = Users.insert {
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
            it[username] = user.username
            it[password] = user.password
        } > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }

    override suspend fun loginUsers(username: String, password: String): User? = dbQuery {
        Users.select { Users.username eq username and (Users.password eq password) }
            .map(::resultRowUser)
            .singleOrNull()
    }

}

val dao: DAOFacade = DAOFacadeImpl().apply { runBlocking { DatabaseSingleton.init() } }
