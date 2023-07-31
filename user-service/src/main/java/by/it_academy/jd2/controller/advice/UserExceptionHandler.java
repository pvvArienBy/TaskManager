package by.it_academy.jd2.controller.advice;

import by.it_academy.jd2.core.errors.ErrorResponse;
import by.it_academy.jd2.core.errors.StructuredErrorResponse;
import by.it_academy.jd2.core.enums.ErrorType;
import by.it_academy.jd2.service.exceptions.EntityNotFoundException;
import by.it_academy.jd2.service.exceptions.NotCorrectValueException;
import by.it_academy.jd2.service.exceptions.UniqueConstraintViolation;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<StructuredErrorResponse> handleInvalidArgument(
            ConstraintViolationException ex) {

        StructuredErrorResponse response = new StructuredErrorResponse(
                ErrorType.STRUCTURED_ERROR, new HashMap<>());
        ex.getConstraintViolations().stream().forEach(violation -> {
            response.getErrorMap().put(violation.getPropertyPath().toString(), violation.getMessage());
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UniqueConstraintViolation.class})
    public ResponseEntity<StructuredErrorResponse> handleInvalidArgument(
            UniqueConstraintViolation ex) {

        StructuredErrorResponse response = new StructuredErrorResponse(
                ErrorType.STRUCTURED_ERROR, new HashMap<>());

        StackTraceElement[] stackTrace = ex.getStackTrace();
        String methodName = stackTrace[0].getMethodName() + "." + ex.getMessageField();
        response.getErrorMap().put(methodName, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class,
            IOException.class,
            IndexOutOfBoundsException.class,
            ArithmeticException.class,
            Error.class,
            EntityNotFoundException.class
    })
    public ResponseEntity<List<ErrorResponse>> handleInnerError(Exception ex) {
        List<ErrorResponse> errorList = new ArrayList<>();
        String errorMessage = ex.getMessage();
        ErrorResponse error = new ErrorResponse(ErrorType.ERROR, " " + errorMessage);
        errorList.add(error);

        return new ResponseEntity<>(errorList, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StructuredErrorResponse> handleJsonErrors(HttpMessageNotReadableException ex) {

        StructuredErrorResponse response = new StructuredErrorResponse(
                ErrorType.STRUCTURED_ERROR, new HashMap<>());

        if (ex.getMessage().contains("Enum class: [USER, ADMIN]")) {
            response.getErrorMap().put("save_role", "Не верно указан role!");
        } else if (ex.getMessage().contains("Enum class: [DEACTIVATED, ACTIVATED, WAITING_ACTIVATION]")) {
            response.getErrorMap().put("save_status", "Не верно указан status!");
        } else response.getErrorMap().put(ex.getCause().toString(), ex.getMessage());

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<List<ErrorResponse>> handleAuthenticationException(AuthenticationException ex) {
        List<ErrorResponse> errorList = new ArrayList<>();
        ErrorResponse error;

        if (ex.getMessage().contains("Bad credentials")) {
            error = new ErrorResponse(ErrorType.ERROR,
                    "Логин или пароль содержат некорректные данные. Попробуйте ещё раз");
        } else error = new ErrorResponse(ErrorType.ERROR, ex.getMessage());

        errorList.add(error);

        return new ResponseEntity(errorList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StructuredErrorResponse> authorizationInvalidArgument(
            MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();

        StructuredErrorResponse response = new StructuredErrorResponse(ErrorType.STRUCTURED_ERROR, new HashMap<>());

        for (ObjectError error : allErrors) {
            String fieldName = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
            response.getErrorMap().put("authorization." + fieldName, error.getDefaultMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotCorrectValueException.class)
    public ResponseEntity<List<ErrorResponse>> handleNotCorrectValueException(NotCorrectValueException ex) {
        return new ResponseEntity(ex.getValues(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<List<ErrorResponse>> handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        List<ErrorResponse> errorList = new ArrayList<>();
        errorList.add(new ErrorResponse(ErrorType.ERROR,
                "Не корректные данные в передаваемом параметре - " + ex.getName()));

        return new ResponseEntity(errorList, HttpStatus.BAD_REQUEST);
    }

    // TODO: 28.07.2023 problem exception (403)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<List<ErrorResponse>> handleJwtException(ExpiredJwtException ex) {
        System.out.println(ex.getMessage());
        System.out.println(ex.getCause().getMessage());
//        System.out.println(ex.getHeader());
        System.out.println(ex.getSuppressed());
//        System.out.println(ex.getClaims());
        System.out.println(ex.getMessage());
        System.out.println(ex.getMessage());
        List<ErrorResponse> errorList = new ArrayList<>();
        errorList.add(new ErrorResponse(ErrorType.ERROR,
                "JWT "));

        return new ResponseEntity(errorList, HttpStatus.BAD_REQUEST);
    }
}
