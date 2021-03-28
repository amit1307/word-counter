package com.amit.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WordCounterExceptionHandler {

    @ExceptionHandler(InvalidWordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidWordException(final InvalidWordException exception) {
        return ExceptionHandlerHelper.errorResponse(HttpStatus.BAD_REQUEST, WebErrorType.BAD_FORMAT, exception.getMessage());
    }

    public ResponseEntity<ErrorResponse> handleTranslationException(TranslationException exception) {
        return ExceptionHandlerHelper.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, WebErrorType.TRANSLATION_ERROR, exception.getMessage());
    }
}
