package by.it_academy.jd2.service.exceptions;

import by.it_academy.jd2.core.dto.ErrorDTO;

import java.util.List;

public  class NotCorrectValueException extends RuntimeException {
    private final List<ErrorDTO> values;

    public NotCorrectValueException(List<ErrorDTO> values) {
        this.values = values;
    }

    public List<ErrorDTO> getValues() {
        return values;
    }
}