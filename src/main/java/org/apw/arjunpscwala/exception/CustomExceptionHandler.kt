package org.apw.arjunpscwala.exception

import org.apw.arjunpscwala.model.StandardError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(InvalidTokenException::class)
    fun handleInvalidTokenException(ex: InvalidTokenException): ResponseEntity<StandardError> {
        return ResponseEntity<StandardError>(
            StandardError(ex.message ?: "", HttpStatus.BAD_REQUEST.value(), timestamp = java.util.Date()), HttpStatus.BAD_REQUEST
        )
    }
}
