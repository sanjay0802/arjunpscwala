package com.arjunpscwala.pscwala.android

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjunpscwala.pscwala.PhoneAuthInfoAndroid
import com.arjunpscwala.pscwala.models.ErrorMessage
import com.arjunpscwala.pscwala.utils.Event
import com.arjunpscwala.pscwala.utils.EventBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val DEFAULT_COUNTDOWN_TIME_OUT = 60 * 1000L

class VerifyOTPViewModel : ViewModel() {

    private val eventBusJob = viewModelScope.launch {
        EventBus.events.collect { event ->
            if (event is Event.NavigateEvent<*>) {
                val data: PhoneAuthInfoAndroid = event.data as PhoneAuthInfoAndroid


            }
        }
    }
    private val _verifyOTPUIState = MutableStateFlow(VerifyOTPUIState())
    val verifyOTPUIState = _verifyOTPUIState.asStateFlow()

    init {
        startCountdown(DEFAULT_COUNTDOWN_TIME_OUT)
    }

    fun startCountdown(millisInFuture: Long) {
        object : CountDownTimer(millisInFuture, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                _verifyOTPUIState.value = _verifyOTPUIState.value.copy(
                    countdownText = seconds.toString(), countdownFinished = false
                )

                Log.d("saurabhTAg", "countdown running $seconds")
            }

            override fun onFinish() {
                Log.d("saurabhTAg", "countdown finished")
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

}

data class VerifyOTPUIState(
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val countdownText: String = "60",
    val countdownFinished: Boolean = false,
)