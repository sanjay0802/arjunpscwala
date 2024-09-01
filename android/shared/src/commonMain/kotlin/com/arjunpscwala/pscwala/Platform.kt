package com.arjunpscwala.pscwala

import kotlinx.serialization.Serializable


@Serializable
abstract class PhoneAuthInfo


expect fun randomUUID(): Long
expect fun phoneAuthInfo(verificationId: String, phone: String): PhoneAuthInfo

expect object AppContext
