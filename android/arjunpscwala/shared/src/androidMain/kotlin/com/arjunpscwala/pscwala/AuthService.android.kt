package com.arjunpscwala.pscwala

import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

class AndroidAuthService : AuthService {
    private val auth = Firebase.auth


    override suspend fun sendOTP(phoneNumber: String): Result<PhoneAuthInfo> {
        return suspendCancellableCoroutine { continuation ->
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(AppContext.currentActivity?.get()!!)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(crendential: PhoneAuthCredential) {

                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        continuation.resumeWith(Result.failure(Exception(e.message)))
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        continuation.resume(
                            Result.success(
                                phoneAuthInfo(verificationId, phoneNumber)
                            )
                        )
                    }
                }) // OnVerificationStateChangedCallbacks
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }

    }
}

actual fun getAuthService(): AuthService = AndroidAuthService()