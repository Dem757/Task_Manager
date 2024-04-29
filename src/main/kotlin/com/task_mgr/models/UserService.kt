package com.task_mgr.models

import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

class UserService {
    private val client = KMongo.createClient()
    private val database = client.getDatabase("task")
    private val userCollection = database.getCollection<User>()

    fun register(user: User): Id<User>? {
        userCollection.insertOne(user)
        return user.id
    }

    fun login(username: String, password: String): User? {
        return userCollection.findOne(User::username eq username, User::password eq password)
    }
}