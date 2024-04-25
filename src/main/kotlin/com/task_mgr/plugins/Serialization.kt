package com.task_mgr.plugins

import com.task_mgr.models.Task
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
    val tasks = mutableListOf<Task>()
    routing {
        route("/tasks") {
            get {
                call.respond(tasks)
            }
            post {
                val task = call.receive<Task>()
                tasks.add(task)
                call.respond(task)
            }
        }
    }
}