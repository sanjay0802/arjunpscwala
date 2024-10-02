package org.apw.arjunpscwala.security

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import kotlin.jvm.Throws

private const val authUrls = "/apw/auth/**"

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
open class SecurityConfig {


    @Bean
    @Throws(Exception::class)
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.exceptionHandling { exceptionHandling ->
            exceptionHandling.authenticationEntryPoint(authenticationEntryPoint())

        }.csrf { csrf ->
            csrf.disable()
        }.authorizeHttpRequests { authz ->
            authz
                .requestMatchers(authUrls)
                .permitAll()
                .requestMatchers("/error/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        }
            .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .build()

    }


    @Bean
    open fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return CustomAuthenticationEntryPoint()
    }

    @Bean
    open fun jwtRequestFilter(): JwtRequestFilter {
        return JwtRequestFilter()
    }
}
