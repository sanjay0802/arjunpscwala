package com.arjunpscwala.pscwala

interface TimerService {

    fun startCountDown(
        millisInFuture: Long,
        onTick: (millisUntilFinished: Long) -> Unit, onFinish: () -> Unit
    )
}


expect fun getTimerService(): TimerService