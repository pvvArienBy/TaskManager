package by.it_academy.jd2.service.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;

public class TestListExceptions extends ConstraintViolationException {
    public TestListExceptions(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(message, constraintViolations);
    }

    public TestListExceptions(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }
}
