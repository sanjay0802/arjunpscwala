package com.arjunpscwala.pscwala.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkClient {
    private const val DEFAULT_TIMEOUT = 15000L
    private const val DEFAULT_HOST = "api.arjunpscwala.com/apw"
    private val DEFAULT_PROTOCOL = URLProtocol.HTTP
    val jsonConfig = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    val httpClient by lazy {
        HttpClient() {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(jsonConfig)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = DEFAULT_TIMEOUT
            }
            defaultRequest {
                host = DEFAULT_HOST
                url {
                    protocol = DEFAULT_PROTOCOL
                }
            }
        }
    }
}