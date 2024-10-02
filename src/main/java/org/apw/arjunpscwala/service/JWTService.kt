package org.apw.arjunpscwala.service


import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date


@Service
class JWTService {
    @Value("\${security.jwt.secret-key}")
    private val secretKey: String? = null

    private val jwtExpiration: Long = 7 * 24 * 60 * 60 * 1000L

    fun generateToken(userId: Long): String {
        return JWT
            .create()
            .withClaim("userId", userId)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + jwtExpiration))
            .sign(Algorithm.HMAC256(secretKey))
    }


    fun extractUserId(token: String?): Long {
        return decodeToken(token).getClaim("userId").asLong()
    }

    private fun decodeToken(token: String?): DecodedJWT {
        return JWT.require(Algorithm.HMAC256(secretKey))
            .build()
            .verify(token)
    }

    fun validateToken(token: String?, userId: Long?): Boolean {
        val extractedUserId: Long = extractUserId(token)
        return (extractedUserId == userId && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String?): Boolean {
        return decodeToken(token).expiresAt.before(Date())
    }
}