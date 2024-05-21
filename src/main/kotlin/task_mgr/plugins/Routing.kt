package task_mgr.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import task_mgr.models.*
import task_mgr.dao.dao
import io.ktor.http.*
import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import java.io.File
import java.security.MessageDigest
import java.util.*
import kotlin.collections.ArrayList

fun Application.configureRouting() {

    //val service = TaskService()

    val userService = UserService()

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()

    JwtConfig.init(secret, issuer, audience)

    routing {
        staticFiles("/", File("files")) {
            default("index.html")
            preCompressed(CompressedFileType.BROTLI, CompressedFileType.GZIP)
        }
        route("/api/user") {
            post("/register") {
                val request = call.receive<UserDto>()
                val user = request.toUser().copy(password = hashPassword(request.password))

                userService.register(user)
                    ?.let { userId ->
                        call.response.headers.append("User-Id-Header", userId.toString())
                        call.respond(HttpStatusCode.Created)
                    } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST_RESPONSE)
            }
            post("/login") {
                val request = call.receive<UserDto>()
                val user = request.toUser().copy(password = hashPassword(request.password))
                userService.login(user.username, user.password)
                    ?.let { loggedInUser ->
                        val token = JwtConfig.generateToken(loggedInUser)
                        call.respond(HttpStatusCode.OK, mapOf("token" to token))
                    } ?: call.respond(HttpStatusCode.Unauthorized, ErrorResponse("Invalid username or password"))
            }
        }
        authenticate("auth-jwt") {
            route("/api/task") {
                post {
                    val request = call.receive<TaskDto>()
                    val name = request.name
                    val description = request.description
                    val label = request.label
                    val owner = request.owner
                    val status = request.status
                    val dueDate = request.dueDate


                    dao.addTask(name, description, label, owner, status, dueDate)

                }
                get {
                    val taskList = dao.getAllTasks().map(Task::toDto)

                    call.respond(taskList)
                }
                get("/{id}") {
                    val id = call.parameters["id"]

                    id?.let { it1 ->
                        dao.getTask(it1.toInt())
                            ?.let { task -> call.respond(task.toDto()) }
                    }
                        ?: call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)

                }
                put("/{id}") {
                    val id = call.parameters["id"].toString()
                    val request = call.receive<TaskDto>()


                    val updatedSuccessfully = dao.updateTask(request.toTask(id.toInt()))

                    if (updatedSuccessfully) {
                        call.respond(HttpStatusCode.OK)
                    } else {
                        call.respond(HttpStatusCode.NotFound, ErrorResponse.BAD_REQUEST_RESPONSE)
                    }
                }
                delete("/{id}") {
                    val id = call.parameters["id"].toString()

                    val deletedSuccessfully = dao.deleteTask(id.toInt())

                    if (deletedSuccessfully) {
                        call.respond(HttpStatusCode.OK)
                    } else {
                        call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
                    }
                }
            }
        }
    }
}

// Some basic hashing (not too secure but better than plain text)
fun hashPassword(password: String): String {
    val bytes = password.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}
