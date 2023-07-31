package by.it_academy.jd2.service.exceptions;

import by.it_academy.jd2.core.errors.ErrorResponse;

import java.util.List;

public  class NotCorrectValueException extends RuntimeException {
    private final List<ErrorResponse> values;

    public NotCorrectValueException(List<ErrorResponse> values) {
        this.values = values;
    }

    public List<ErrorResponse> getValues() {
        return values;
    }
}