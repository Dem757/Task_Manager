package com.task_mgr.plugins

import com.task_mgr.models.UserService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.configureSecurity() {
    val userService = UserService()
    install(Authentication) {
        jwt {
            realm = "com.task_mgr"
            verifier(JwtConfig.verifier)
            validate {
                val name = it.payload.getClaim("name").asString()
                val user = userService.findByName(name)
                if (user != null) UserIdPrincipal(name) else null
            }
        }
    }
    data class MySession(val count: Int = 0)
    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
    routing {
        get("/session/increment") {
            val session = call.sessions.get<MySession>() ?: MySession()
            call.sessions.set(session.copy(count = session.count + 1))
            call.respondText("Counter is ${session.count}. Refresh to increment.")
        }
    }
}
