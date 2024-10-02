package org.apw.arjunpscwala.exception

import org.springframework.security.core.AuthenticationException

class UserNotFoundException(message: String) : AuthenticationException(message)