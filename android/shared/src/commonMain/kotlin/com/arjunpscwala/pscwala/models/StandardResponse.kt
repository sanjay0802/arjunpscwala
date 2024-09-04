package com.arjunpscwala.pscwala.models

import kotlinx.serialization.Serializable

@Serializable
class StandardResponse<T> {
    val msg: String? = null
    val status: Int? = null
    val response: T? = null

}