package com.arjunpscwala.pscwala.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object EventBus {
    // MutableSharedFlow with replay = 0, as we don't need to replay old events
    private val _events = MutableSharedFlow<Any>(replay = 1)
    val events: SharedFlow<Any> = _events

    // Function to emit an event
    suspend fun emit(event: Any) {
        _events.emit(event)
    }
}

sealed class Event {
    data class MessageEvent(val message: String) : Event()
    data class ErrorEvent(val error: Throwable) : Event()
    data class NavigateEvent<T>(val data: T) : Event()
    // Add other event types here
}

