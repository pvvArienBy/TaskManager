package by.it_academy.jd2.core.errors;

import by.it_academy.jd2.core.enums.ErrorType;

public class ErrorResponse {
    private ErrorType errorType;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
