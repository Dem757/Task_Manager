package com.task_mgr.plugins

import com.task_mgr.models.*
import io.ktor.http.*
import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {

    val service = TaskService()

    val userService = UserService()

    routing {
        staticFiles("/", File("files")) {
            default("index.html")
            preCompressed(CompressedFileType.BROTLI, CompressedFileType.GZIP)
        }
//        singlePageApplication {
//            useResources = true
//            filesPath = "web-app"
//            defaultPage = "index.html"
//            ignoreFiles { it.endsWith(".txt") }
//        }
        route("/api/task") {
            post {
                val request = call.receive<TaskDto>()
                val task = request.toTask()

                service.create(task)
                    ?.let { userId ->
                        call.response.headers.append("User-Id-Header", userId.toString())
                        call.respond(HttpStatusCode.Created)
                    } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST_RESPONSE)
            }
            get {
                val taskList = service.findAll().map(Task::toDto)
                call.respond(taskList)
            }
            get("/{id}") {
                val id = call.parameters["id"]

                id?.let { it1 ->
                    service.findById(it1)
                        ?.let { task -> call.respond(task.toDto()) }
                }
                    ?: call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
            }
            put("/{id}") {
                val id = call.parameters["id"].toString()
                val request = call.receive<TaskDto>()
                val task = request.toTask()

                val updatedSuccessfully = service.update(id, task)

                if (updatedSuccessfully) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NotFound, ErrorResponse.BAD_REQUEST_RESPONSE)
                }
            }
            delete("/{id}") {
                val id = call.parameters["id"].toString()

                val deletedSuccessfully = service.delete(id)

                if (deletedSuccessfully) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
                }
            }
            get("/search") {
                val name = call.request.queryParameters["name"].toString()

                val foundName = service.findByName(name).map(Task::toDto)

                call.respond(foundName)
            }
        }
        route("/api/user") {
            post("/register") {
                val request = call.receive<UserDto>()
                val user = request.toUser()

                userService.register(user)
                    ?.let { userId ->
                        call.response.headers.append("User-Id-Header", userId.toString())
                        call.respond(HttpStatusCode.Created)
                    } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST_RESPONSE)
            }
            post("/login") {
                val user = call.receive<User>()
                userService.login(user.username, user.password)
                    ?.let { loggedInUser ->
                        call.response.headers.append("User-Id-Header", loggedInUser.id.toString())
                        call.respond(HttpStatusCode.OK)
                    } ?: call.respond(HttpStatusCode.Unauthorized, ErrorResponse("Invalid username or password"))
            }
        }
    }
}
