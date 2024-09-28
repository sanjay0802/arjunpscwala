package com.arjunpscwala.pscwala.repository

import com.arjunpscwala.pscwala.AuthService
import com.arjunpscwala.pscwala.PhoneAuthInfo
import com.arjunpscwala.pscwala.getAuthService
import com.arjunpscwala.pscwala.models.response.LoginInfo
import com.arjunpscwala.pscwala.models.StandardResponse
import com.arjunpscwala.pscwala.models.request.ProfileRequest
import com.arjunpscwala.pscwala.models.request.SignUpRequest
import com.arjunpscwala.pscwala.models.request.VerifyOTPRequest
import com.arjunpscwala.pscwala.network.NetworkClient
import com.arjunpscwala.pscwala.network.registerUser
import com.arjunpscwala.pscwala.network.updateProfile
import com.arjunpscwala.pscwala.network.verifyOTP
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


interface AuthRepository {
    suspend fun signInUsingMobile(mobileNumber: String): Result<PhoneAuthInfo>
    suspend fun verifyUser(
        mobileNumber: String,
        verificationId: String
    ): StandardResponse<LoginInfo>

    suspend fun signupUser(
        signUpRequest: SignUpRequest
    ): StandardResponse<LoginInfo>


    suspend fun updateProfile(
        profileRequest: ProfileRequest
    ): StandardResponse<LoginInfo>
}

private const val DEFAULT_COUNTRY_CODE = "+91"

class AuthRepositoryImpl : AuthRepository {

    private val authService: AuthService = getAuthService()

    override suspend fun signInUsingMobile(mobileNumber: String): Result<PhoneAuthInfo> {
        return authService.sendOTP(DEFAULT_COUNTRY_CODE.plus(mobileNumber))
    }

    override suspend fun verifyUser(
        mobileNumber: String,
        verificationId: String
    ): StandardResponse<LoginInfo> {


        return NetworkClient.httpClient.post(verifyOTP) {
            contentType(ContentType.Application.Json)
            setBody(
                VerifyOTPRequest(
                    mobileNumber.removePrefix(DEFAULT_COUNTRY_CODE),
                    verificationId
                )
            )
        }.body()

    }

    override suspend fun signupUser(signUpRequest: SignUpRequest): StandardResponse<LoginInfo> {
        val request = signUpRequest.copy(
            mobileNo = signUpRequest.mobileNo.removePrefix(
                DEFAULT_COUNTRY_CODE
            )
        )
        return NetworkClient.httpClient.post(registerUser) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun updateProfile(profileRequest: ProfileRequest): StandardResponse<LoginInfo> {
        val request = profileRequest.copy(
            mobileNo = profileRequest.mobileNo.removePrefix(
                DEFAULT_COUNTRY_CODE
            )
        )
        return NetworkClient.httpClient.post(updateProfile) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }


}