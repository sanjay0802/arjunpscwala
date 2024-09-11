package com.arjunpscwala.pscwala.android

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjunpscwala.pscwala.PhoneAuthInfoAndroid
import com.arjunpscwala.pscwala.models.ErrorMessage
import com.arjunpscwala.pscwala.models.state.UIState
import com.arjunpscwala.pscwala.randomUUID
import com.arjunpscwala.pscwala.repository.AuthRepository
import com.arjunpscwala.pscwala.repository.AuthRepositoryImpl
import com.arjunpscwala.pscwala.utils.Event
import com.arjunpscwala.pscwala.utils.EventBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val defaultTime: String = "60"
private const val DEFAULT_COUNTDOWN_TIME_OUT = 60 * 1000L

class VerifyOTPViewModel : ViewModel() {


    private lateinit var data: PhoneAuthInfoAndroid
    private val _verifyOTPUIState = MutableStateFlow(VerifyOTPUIState())
    val verifyOTPUIState = _verifyOTPUIState.asStateFlow()

    private val eventBusJob = viewModelScope.launch {
        EventBus.events.collect { event ->
            if (event is Event.NavigateEvent<*>) {
                data = event.data as PhoneAuthInfoAndroid
                _verifyOTPUIState.update {
                    it.copy(
                        phoneNumber = data.phone, verificationId = data.verificationId
                    )
                }

            }
        }
    }

    private val authRepository: AuthRepository = AuthRepositoryImpl()

    init {
        startCountdown(DEFAULT_COUNTDOWN_TIME_OUT)
    }

    private fun startCountdown(millisInFuture: Long) {
        object : CountDownTimer(millisInFuture, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                _verifyOTPUIState.value = _verifyOTPUIState.value.copy(
                    countdownText = seconds.toString(), countdownFinished = false
                )

            }

            override fun onFinish() {
                _verifyOTPUIState.value = _verifyOTPUIState.value.copy(
                    countdownFinished = true
                )
            }
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        eventBusJob.cancel()
    }

    fun verifyUser() {
        viewModelScope.launch {
            try {
                _verifyOTPUIState.update {
                    it.copy(isLoading = true)
                }
                val result = authRepository.verifyUser(
                    _verifyOTPUIState.value.phoneNumber,
                    _verifyOTPUIState.value.verificationId
                )
                if (result.status == 401) {
                    _verifyOTPUIState.update {
                        it.copy(isLoading = false, navToRegister = true)
                    }
                } else {
                    _verifyOTPUIState.update {
                        it.copy(isLoading = true, navToLogin = true)
                    }
                }

            } catch (e: Exception) {
                _verifyOTPUIState.update {
                    val errorMessages = it.errorMessages + ErrorMessage(
                        id = randomUUID(),
                        message = e.message ?: ""
                    )
                    it.copy(errorMessages = errorMessages, isLoading = false)
                }
            }
        }

    }

    fun sendOTP() {

        viewModelScope.launch {
            try {
                _verifyOTPUIState.update {
                    it.copy(isLoading = true)
                }
                val result = authRepository.signInUsingMobile(data.phone)
                if (result.isSuccess) {
                    startCountdown(DEFAULT_COUNTDOWN_TIME_OUT)
                    _verifyOTPUIState.update {
                        it.copy(
                            isLoading = false,
                            countdownFinished = false,
                        )
                    }
                } else if (result.isFailure) {

                    _verifyOTPUIState.update {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = randomUUID(),
                            message = result.exceptionOrNull()?.message ?: ""
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }

            } catch (e: Exception) {
                _verifyOTPUIState.update {
                    val errorMessages = it.errorMessages + ErrorMessage(
                        id = randomUUID(),
                        message = e.message ?: ""
                    )
                    it.copy(isLoading = false, errorMessages = errorMessages)
                }
            }


        }

    }
}

data class VerifyOTPUIState(
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val navToRegister: Boolean = false,
    val navToLogin: Boolean = false,
    val phoneNumber: String = "",
    val verificationId: String = "",
    val countdownText: String = defaultTime,
    val countdownFinished: Boolean = false,
) : UIState