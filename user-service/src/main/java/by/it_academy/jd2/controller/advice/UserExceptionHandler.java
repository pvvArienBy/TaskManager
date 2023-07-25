package by.it_academy.jd2.controller.advice;


import by.it_academy.jd2.core.dto.ErrorDTO;
import by.it_academy.jd2.core.enums.ErrorType;
import by.it_academy.jd2.core.dto.StructuredErrorDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.HashMap;


@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ConstraintViolationException.class,})
    public ResponseEntity<StructuredErrorDTO> handleInvalidArgument(
            ConstraintViolationException violationException) {

        StructuredErrorDTO response = new StructuredErrorDTO(
                ErrorType.STRUCTURED_ERROR, new HashMap<>());
        violationException.getConstraintViolations().stream().forEach(violation -> {
            response.getErrorMap().put(violation.getPropertyPath().toString(), violation.getMessage());
        });


        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler({HttpMessageNotReadableException.class})
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public ResponseEntity<ErrorDTO> handleBadRequest() {
//        ErrorDTO response = new ErrorDTO(
//                ErrorType.ERROR, "В запроесе не корректные данные");
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler({IllegalArgumentException.class,
            IOException.class,
            IndexOutOfBoundsException.class,
            IllegalArgumentException.class,
            ArithmeticException.class, Error.class})
    public ResponseEntity<ErrorDTO> handleInnerError() {
        ErrorDTO response = new ErrorDTO(
                ErrorType.ERROR, "Внутренняя ошибка сервера. Сервер не смог корректно обработать запрос");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
