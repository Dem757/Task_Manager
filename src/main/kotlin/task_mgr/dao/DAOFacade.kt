package task_mgr.dao

import task_mgr.models.*

interface DAOFacade {
    suspend fun addTask(name: String, description: String, label: String, owner: String, status: String, dueDate:String): Task?
    suspend fun getTask(id: Int): Task?
    suspend fun getAllTasks(): List<Task>
    suspend fun updateTask(task: Task): Boolean
    suspend fun deleteTask(id: Int): Boolean
//    suspend fun addUser(id: Int, username: String, password: String): User?
//    suspend fun getUser(id: Int): User?
//    suspend fun getAllUsers(): List<User>
//    suspend fun updateUser(user: User): Boolean
//    suspend fun deleteUser(id: Int): Boolean
}