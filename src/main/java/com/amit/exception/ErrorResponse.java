package com.amit.exception;

public class ErrorResponse {

    private final String errorCode;
    private final String description;

    public ErrorResponse(WordCountError errorCode, String description) {
        this.errorCode = errorCode.getErrorCode();
        this.description = errorCode.getInterpolatedMessage(description);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }
}
