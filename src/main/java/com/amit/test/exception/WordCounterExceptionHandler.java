package com.amit.test.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.amit.test.exception.WebErrorType.BAD_FORMAT;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WordCounterExceptionHandler {
    @ExceptionHandler(InvalidWordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidWordException(final InvalidWordException exception) {
        return ExceptionHandlerHelper.errorResponse(HttpStatus.BAD_REQUEST, BAD_FORMAT, exception.getMessage());
    }
}
