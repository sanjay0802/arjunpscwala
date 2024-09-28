package com.arjunpscwala.pscwala.models.request

import com.arjunpscwala.pscwala.utils.kCity
import com.arjunpscwala.pscwala.utils.kCourse
import com.arjunpscwala.pscwala.utils.kGender
import com.arjunpscwala.pscwala.utils.kMobileNo
import com.arjunpscwala.pscwala.utils.kUserName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileRequest(
    @SerialName(kMobileNo)
    val mobileNo: String,
    @SerialName(kGender)
    val gender: String,
    @SerialName(kCity)
    val city: String,
    @SerialName(kCourse)
    val course: String
)