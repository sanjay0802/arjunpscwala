package com.arjunpscwala.pscwala

import android.os.CountDownTimer

class AndroidTimerService : TimerService {
    override fun startCountDown(
        millisInFuture: Long,
        onTick: (millisUntilFinished: Long) -> Unit,
        onFinish: () -> Unit
    ) {
        object : CountDownTimer(millisInFuture, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                onTick(millisUntilFinished)

            }

            override fun onFinish() {
                onFinish()
            }
        }.start()
    }
}

actual fun getTimerService(): TimerService = AndroidTimerService()