package by.it_academy.jd2.core.errors;


import by.it_academy.jd2.core.enums.ErrorType;

import java.util.Map;

public class StructuredErrorResponse {
    private ErrorType errorType;
    private Map<String,String> errorMap;

    public StructuredErrorResponse() {
    }

    public StructuredErrorResponse(ErrorType errorType, Map<String, String> errorMap) {
        this.errorType = errorType;
        this.errorMap = errorMap;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(Map<String, String> errorMap) {
        this.errorMap = errorMap;
    }
}
