package com.arjunpscwala.pscwala.models

data class SnackbarState(
    val message: String = "",
    val errorMessages: List<ErrorMessage>,
    val onMessageDismiss: (messageId: Long) -> Unit = {

    }
)