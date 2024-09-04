package com.arjunpscwala.pscwala.models.request

import com.arjunpscwala.pscwala.utils.kFbToken
import com.arjunpscwala.pscwala.utils.kMobileNo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyOTPRequest(
    @SerialName(kMobileNo) val mobileNo: String, @SerialName(kFbToken) val verificationId: String
)