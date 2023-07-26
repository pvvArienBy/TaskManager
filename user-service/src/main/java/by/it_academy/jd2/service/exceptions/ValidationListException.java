package by.it_academy.jd2.service.exceptions;

import java.util.List;

public class ValidationListException extends Exception {
    private final List<String> errors;

    public ValidationListException(List<String> errors) {
        super("Validation failed with " + errors.size() + " errors");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}