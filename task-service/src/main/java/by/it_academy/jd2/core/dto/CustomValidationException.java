package by.it_academy.jd2.core.dto;

import java.util.List;
import java.util.Map;

public class CustomValidationException extends RuntimeException {
    private Map<String, String> errors;

    public CustomValidationException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}