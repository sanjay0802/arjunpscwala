package org.apw.arjunpscwala.exception;

import org.apw.arjunpscwala.model.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class CustomExceptionHandler {


 /*   @ExceptionHandler(value = ApwException.class)
    public StandardResponse handleCustomerAlreadyExistsException(ApwException ex) {

        System.out.println("This is Custom Exception");
        String message = ex.getMessage();

    return StandardResponse.failure(message,HttpStatus.BAD_REQUEST.value());
    }*/
}
