package com.amit.exception;

public enum WebErrorType implements WordCountError {
    BAD_FORMAT("WC_00001", "Bad Format: %s");

    private final String errorCode;
    private final String message;

    WebErrorType(String code, String errorMessage) {
        this.errorCode = code;
        this.message = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}