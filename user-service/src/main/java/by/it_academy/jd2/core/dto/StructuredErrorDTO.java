package by.it_academy.jd2.core.dto;


import by.it_academy.jd2.core.enums.ErrorType;

import java.util.Map;

public class StructuredErrorDTO {
    private ErrorType errorType;
    private Map<String,String> errorMap;

    public StructuredErrorDTO() {
    }

    public StructuredErrorDTO(ErrorType errorType, Map<String, String> errorMap) {
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
