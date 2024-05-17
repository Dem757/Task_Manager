package task_mgr.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import task_mgr.models.UserService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.configureSecurity() {

    val secret = "59ba4fd4b5aab9f7fffecc9a45edb0475ef5e0e643f1ef05d3aef0cba0d1082d"
    val issuer = "com.task_mgr"
    val audience = "http://127.0.0.1:8080/task-board"
    val myRealm = "Access to 'task-board'"

    install(Authentication) {
        jwt("auth-jwt") {
            validate { credential ->
                if (credential.payload.audience.contains(audience)) JWTPrincipal(credential.payload) else null
            }
            realm = myRealm
            verifier(
                JWT
                .require(Algorithm.HMAC512(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build())
        }
    }
}
