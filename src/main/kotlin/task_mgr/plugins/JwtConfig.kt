package task_mgr.plugins

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import task_mgr.models.User
import java.util.*

object JwtConfig {
    private const val secret = ""
    private const val issuer = "com.task_mgr"
    private const val validityInMs = 36_000_00 * 10 // 10 hours
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT.require(algorithm).withIssuer(issuer).build()

    fun generateToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("id", user.id.toString())
        .withClaim("name", user.username)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}