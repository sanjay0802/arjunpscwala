package org.apw.arjunpscwala.security

import com.auth0.jwt.exceptions.JWTDecodeException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apw.arjunpscwala.model.AuthUserDetails
import org.apw.arjunpscwala.service.AccountDetailsService
import org.apw.arjunpscwala.service.JWTService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@org.springframework.stereotype.Component
class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var jwtService: JWTService

    @Autowired
    private lateinit var userDetailsService: AccountDetailsService

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val authorizationHeader: String? = request.getHeader("Authorization")

        var userId: Long? = null
        var jwt: String? = null

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7)
                userId = jwtService.extractUserId(jwt)
            }

            if (userId != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails: AuthUserDetails = this.userDetailsService.loadUserById(userId)
                if (jwtService.validateToken(jwt, userDetails.userId)) {
                    val authenticationToken: UsernamePasswordAuthenticationToken =
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                }
            }
            chain.doFilter(request, response)
        } catch (_e: JWTDecodeException) {
            chain.doFilter(request, response)
        }
    }
}
