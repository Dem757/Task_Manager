package task_mgr.plugins

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import task_mgr.models.User
import java.util.*

object JwtConfig {
    private lateinit var secret: String
    private lateinit var issuer: String
    private lateinit var audience: String
    private const val validityInMs = 36_000_00 * 10 // 10 hours

    fun init(secret: String, issuer: String, audience: String) {
        this.secret = secret
        this.issuer = issuer
        this.audience = audience
    }


    fun generateToken(user: User): String = JWT.create()
        .withAudience(audience)
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("id", user.id.toString())
        .withClaim("name", user.username)
        .withExpiresAt(getExpiration())
        .sign(Algorithm.HMAC512(secret))

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}