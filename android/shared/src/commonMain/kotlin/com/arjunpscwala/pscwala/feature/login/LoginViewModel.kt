package com.arjunpscwala.pscwala.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjunpscwala.pscwala.PhoneAuthInfo
import com.arjunpscwala.pscwala.models.ErrorMessage
import com.arjunpscwala.pscwala.models.state.UIState
import com.arjunpscwala.pscwala.randomUUID
import com.arjunpscwala.pscwala.repository.AuthRepository
import com.arjunpscwala.pscwala.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val authRepository: AuthRepository = AuthRepositoryImpl()
    private val _loginUIState = MutableStateFlow(
        LoginUIState()
    )

    val loginUIState = _loginUIState.asStateFlow()

    fun sendOTP(mobileNumber: String) {
        if (mobileNumber.length < 10) {
            return
        }
        viewModelScope.launch {
            try {
                _loginUIState.update {
                    it.copy(isLoading = true, errorMessages = emptyList())
                }
                val result = authRepository.signInUsingMobile(mobileNumber)
                if (result.isSuccess) {
                    _loginUIState.update {
                        it.copy(isLoading = false, phoneAuthInfo = result.getOrNull())
                    }
                } else if (result.isFailure) {

                    _loginUIState.update {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = randomUUID(),
                            message = result.exceptionOrNull()?.message ?: ""
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }

            } catch (e: Exception) {
                _loginUIState.update {
                    val errorMessages = it.errorMessages + ErrorMessage(
                        id = randomUUID(),
                        message = e.message ?: ""
                    )
                    it.copy(isLoading = false, errorMessages = errorMessages)
                }
            }


        }

    }

    override fun onCleared() {
        super.onCleared()
    }

    fun dispose() {
        _loginUIState.value = LoginUIState()
    }

    fun retryLogin(mobileNumber: String, errorMessageId: Long) {
        _loginUIState.update {
            val errorMessages =
                it.errorMessages.filterNot { errorMessage -> errorMessage.id == errorMessageId }
            it.copy(errorMessages = errorMessages)
        }
        sendOTP(mobileNumber)
    }


}

data class LoginUIState(
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val phoneAuthInfo: PhoneAuthInfo? = null,
) : UIState

