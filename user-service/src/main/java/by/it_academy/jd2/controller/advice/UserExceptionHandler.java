package by.it_academy.jd2.controller.advice;


import by.it_academy.jd2.core.dto.ErrorDTO;
import by.it_academy.jd2.core.dto.StructuredErrorDTO;
import by.it_academy.jd2.core.enums.ErrorType;
import by.it_academy.jd2.service.exceptions.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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


    // TODO: 26.07.2023 протестить параметр а эксепшеном в который заложен  лист
    @ExceptionHandler({IllegalArgumentException.class,
            IOException.class,
            IndexOutOfBoundsException.class,
            ArithmeticException.class,
            Error.class,
            EntityNotFoundException.class
    })
    public ResponseEntity<List<ErrorDTO>> handleInnerError(Exception ex) {
        List<ErrorDTO> errorList = new ArrayList<>();
        String errorMessage = ex.getMessage();
        ErrorDTO error = new ErrorDTO(ErrorType.ERROR, "test " + errorMessage);
        errorList.add(error);
        return new ResponseEntity<>(errorList, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler({
//            IllegalArgumentException.class,
//            IOException.class,
//            IndexOutOfBoundsException.class,
//            ArithmeticException.class,
//            Error.class,
//            EntityNotFoundException.class
//    })
//    public ResponseEntity<List<ErrorDTO>> handleInnerError(Exception ex) {
//        List<ErrorDTO> errorList = new ArrayList<>();
//        String errorMessage = ex.getMessage();
//        ErrorDTO error = new ErrorDTO(ErrorType.ERROR, "eeee " + errorMessage);
//        errorList.add(error);
//        return new ResponseEntity<>(errorList, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
