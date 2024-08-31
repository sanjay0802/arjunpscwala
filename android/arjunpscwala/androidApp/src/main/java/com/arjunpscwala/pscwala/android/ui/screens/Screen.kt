package com.arjunpscwala.pscwala.android.ui.screens

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Welcome : Screen()

    @Serializable
    data object Login : Screen()

    @Serializable
    data object SignUp : Screen()

    @Serializable
    data object Profile : Screen()

    @Serializable
    data object Home : Screen()

    @Serializable
    data object VerifyOTP : Screen()
}

