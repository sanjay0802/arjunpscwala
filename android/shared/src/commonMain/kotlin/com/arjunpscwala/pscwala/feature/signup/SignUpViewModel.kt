package com.arjunpscwala.pscwala.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjunpscwala.pscwala.PhoneAuthInfo
import com.arjunpscwala.pscwala.models.ErrorMessage
import com.arjunpscwala.pscwala.models.request.SignUpRequest
import com.arjunpscwala.pscwala.models.state.UIState
import com.arjunpscwala.pscwala.randomUUID
import com.arjunpscwala.pscwala.repository.AuthRepository
import com.arjunpscwala.pscwala.repository.AuthRepositoryImpl
import com.arjunpscwala.pscwala.utils.Event
import com.arjunpscwala.pscwala.utils.EventBus
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private lateinit var data: PhoneAuthInfo
    private val _registerUIState = MutableStateFlow(RegisterUIState())
    private val authRepository: AuthRepository = AuthRepositoryImpl()

    val registerUIState = _registerUIState.asStateFlow()

    private val eventBusJob = viewModelScope.launch {
        EventBus.events.collect { event ->
            if (event is Event.NavigateEvent<*>) {
                data = event.data as PhoneAuthInfo

                _registerUIState.update {
                    it.copy(
                        phoneNumber = data.mobileNo, verificationId = data.fbToken
                    )
                }

            }
        }
    }

    fun signUp(
    ) {
        if (_registerUIState.value.fullName.isBlank()) {
            _registerUIState.update {
                val errorMessages = it.errorMessages + ErrorMessage(
                    id = randomUUID(),
                    message = "Full Name is required"
                )
                it.copy(errorMessages = errorMessages, isLoading = false)

            }
            return
        }
        viewModelScope.launch {
            try {
                _registerUIState.update {
                    it.copy(isLoading = true)
                }
                val result = authRepository.signupUser(
                    SignUpRequest(
                        mobileNo = _registerUIState.value.phoneNumber,
                        name = _registerUIState.value.fullName,
                        verificationId = data.fbToken
                    )
                )
                if (result.status != HttpStatusCode.OK.value) {
                    _registerUIState.update {
                        it.copy(isLoading = false, navigateToProfile = true)
                    }
                } else {
                    _registerUIState.update {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = randomUUID(),
                            message = result.msg ?: ""
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }


            } catch (e: Exception) {
                _registerUIState.update {
                    val errorMessages = it.errorMessages + ErrorMessage(
                        id = randomUUID(),
                        message = e.message ?: ""
                    )
                    it.copy(errorMessages = errorMessages, isLoading = false)
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

    fun onUserNameChange(userName: String) {
        _registerUIState.update {
            it.copy(userName = userName)
        }
    }


    fun onFullNameChange(fullName: String) {
        _registerUIState.update {
            it.copy(fullName = fullName)
        }
    }
}


data class RegisterUIState(
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val navigateToProfile: Boolean = false,
    val userName: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val verificationId: String = "",
) : UIState
