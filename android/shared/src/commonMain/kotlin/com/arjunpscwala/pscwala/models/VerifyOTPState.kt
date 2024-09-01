package com.arjunpscwala.pscwala.models

data class VerifyOTPState(
    val loginInfo: LoginInfo? = null,
    val countdownText: String = "60",
    val countdownFinished: Boolean = false,
)