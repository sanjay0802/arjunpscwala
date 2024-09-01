package com.arjunpscwala.pscwala

import kotlinx.serialization.Serializable


@Serializable
data class PhoneAuthInfoAndroid(val verificationId: String = "", val phone: String = "") :
    PhoneAuthInfo()


actual fun phoneAuthInfo(verificationId: String, phone: String): PhoneAuthInfo =
    PhoneAuthInfoAndroid(verificationId, phone)