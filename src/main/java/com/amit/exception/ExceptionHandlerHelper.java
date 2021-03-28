package com.amit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ExceptionHandlerHelper {
    private ExceptionHandlerHelper() {
    }

    public static ResponseEntity<ErrorResponse> errorResponse(final HttpStatus httpStatus, final WebErrorType code, String message) {
        ErrorResponse errorResponse = new ErrorResponse(code, message);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}



