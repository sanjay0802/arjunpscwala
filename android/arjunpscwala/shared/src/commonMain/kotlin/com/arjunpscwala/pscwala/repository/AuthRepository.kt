package com.arjunpscwala.pscwala.repository

import com.arjunpscwala.pscwala.AuthService
import com.arjunpscwala.pscwala.PhoneAuthInfo
import com.arjunpscwala.pscwala.getAuthService


interface AuthRepository {
    suspend fun signInUsingMobile(mobileNumber: String): Result<PhoneAuthInfo>
}

private const val DEFAULT_COUNTRY_CODE = "+91"

class AuthRepositoryImpl : AuthRepository {

    private val authService: AuthService = getAuthService()

    override suspend fun signInUsingMobile(mobileNumber: String): Result<PhoneAuthInfo> {
        return authService.sendOTP(DEFAULT_COUNTRY_CODE.plus(mobileNumber))
    }


}