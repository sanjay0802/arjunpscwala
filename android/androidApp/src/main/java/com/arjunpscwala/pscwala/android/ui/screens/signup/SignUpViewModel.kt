package com.arjunpscwala.pscwala.android.ui.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjunpscwala.pscwala.PhoneAuthInfoAndroid
import com.arjunpscwala.pscwala.models.ErrorMessage
import com.arjunpscwala.pscwala.utils.Event
import com.arjunpscwala.pscwala.utils.EventBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private lateinit var data: PhoneAuthInfoAndroid
    private val _registerUIState = MutableStateFlow(RegisterUIState())

    val registerUIState = _registerUIState.asStateFlow()

    private val eventBusJob = viewModelScope.launch {
        EventBus.events.collect { event ->
            if (event is Event.NavigateEvent<*>) {
                data = event.data as PhoneAuthInfoAndroid
                _registerUIState.update {
                    it.copy(
                        phoneNumber = data.phone, verificationId = data.verificationId
                    )
                }

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        eventBusJob.cancel()
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _registerUIState.update {
            it.copy(phoneNumber = phoneNumber)
        }
    }
}


data class RegisterUIState(
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val phoneNumber: String = "",
    val verificationId: String = "",
)
