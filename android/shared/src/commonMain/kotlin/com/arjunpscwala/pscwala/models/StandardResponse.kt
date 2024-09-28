package com.arjunpscwala.pscwala.models

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
class StandardResponse<T> {


    @SerialName("msg")
    val msg: String? = null

    @SerialName("error")
    val error: String? = null
    val status: Int? = null
    val response: T? = null


    fun parseError(): String {
        return if (this.status in HttpStatusCode.BadRequest.value.rangeTo(HttpStatusCode.NotAcceptable.value)) {
            this.msg
        } else {
            this.error
        } ?: ""
    }
}


