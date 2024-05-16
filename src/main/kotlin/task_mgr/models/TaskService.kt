package task_mgr.models

import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

class TaskService {
    private val client = KMongo.createClient()
    private val database = client.getDatabase("task")
    private val taskCollection = database.getCollection<Task>()

    fun create(task: Task): Id<Task>? {
        taskCollection.insertOne(task)
        return task.id
    }

    fun findAll(): List<Task>  = taskCollection.find().toList()

    fun findById(id: String): Task? {
        val bsonId: Id<Task> = ObjectId(id).toId()
        return taskCollection.findOne(Task::id eq bsonId)
    }

    fun findByName(name: String): List<Task> {
        val caseSensitiveTypeSafeFilter = Task::name regex name
        return taskCollection.find(caseSensitiveTypeSafeFilter).toList()
    }

    fun update(id: String, request: Task): Boolean =
        findById(id)
            ?.let { task ->
                val updateResult = taskCollection.replaceOne(
                    task.copy(
                        name = request.name,
                        description = request.description,
                        label = request.label,
                        owner = request.owner,
                        status = request.status,
                        dueDate = request.dueDate
                    )
                )
                updateResult.modifiedCount == 1L
            } ?: false

    fun delete(id: String): Boolean {
        val deleteResult = taskCollection.deleteOneById(ObjectId(id))
        return deleteResult.deletedCount == 1L
    }
}

