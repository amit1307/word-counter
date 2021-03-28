package com.amit.exception;

public interface WordCountError {
    String getErrorCode();

    String getMessage();

    default String getInterpolatedMessage(Object... parameters) {
        return String.format(getMessage(), parameters);
    }
}
