package com.arjunpscwala.pscwala.models.request

import com.arjunpscwala.pscwala.utils.kFbToken
import com.arjunpscwala.pscwala.utils.kMobileNo
import com.arjunpscwala.pscwala.utils.kName
import com.arjunpscwala.pscwala.utils.kUserName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName(kUserName)
    val userName: String,
    @SerialName(kMobileNo)
    val mobileNo: String,
    @SerialName(kFbToken)
    val verificationId: String,
    @SerialName(kName)
    val name: String
)