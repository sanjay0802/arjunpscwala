package com.arjunpscwala.pscwala


interface AuthService {

    suspend fun sendOTP(phoneNumber: String,): Result<PhoneAuthInfo>


}

expect fun getAuthService(): AuthService