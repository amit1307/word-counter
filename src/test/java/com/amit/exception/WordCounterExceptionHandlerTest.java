package com.amit.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class WordCounterExceptionHandlerTest {

    private WordCounterExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new WordCounterExceptionHandler();
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenInvalidStoredEventRequestExceptionOccurs() {
        // when
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleInvalidWordException(new InvalidWordException("Invalid word"));

        // then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getErrorCode()).isEqualTo("WC_00001");
        assertThat(responseEntity.getBody().getDescription()).isEqualTo("Bad Format: Invalid word");
    }

}