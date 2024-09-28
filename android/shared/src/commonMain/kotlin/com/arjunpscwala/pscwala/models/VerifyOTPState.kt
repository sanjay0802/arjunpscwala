package com.arjunpscwala.pscwala.models

import com.arjunpscwala.pscwala.models.response.LoginInfo

data class VerifyOTPState(
    val loginInfo: LoginInfo? = null,
    val countdownText: String = "60",
    val countdownFinished: Boolean = false,
)