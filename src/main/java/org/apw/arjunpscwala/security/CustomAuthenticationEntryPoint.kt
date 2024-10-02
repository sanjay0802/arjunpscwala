package org.apw.arjunpscwala.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apw.arjunpscwala.model.StandardError
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException
import java.util.Date

class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    public override fun commence(
        req: HttpServletRequest?,
        res: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        val objectMapper = ObjectMapper() // You can also inject this via Spring

        res.setContentType("application/json;charset=UTF-8")
        res.setStatus(403)

        val errorResponse = StandardError(
            error = "Access denied",
            status = HttpStatus.FORBIDDEN.value(),
            timestamp = Date(), // Assuming this returns a String
        )

        val json = objectMapper.writeValueAsString(errorResponse)

        res.getWriter().write(json)
    }
}