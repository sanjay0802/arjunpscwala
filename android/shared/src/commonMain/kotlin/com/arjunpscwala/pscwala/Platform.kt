package com.arjunpscwala.pscwala

import kotlinx.serialization.Serializable


@Serializable
abstract class PhoneAuthInfo(val fbToken: String, val mobileNo: String)


expect fun randomUUID(): Long
expect fun phoneAuthInfo(verificationId: String, phone: String): PhoneAuthInfo

expect object AppContext
