package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.ErrorType;

public class ErrorDTO {
    private ErrorType errorType;
    private String message;

    public ErrorDTO() {
    }

    public ErrorDTO(ErrorType errorType, String message) {
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
