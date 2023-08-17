package by.it_academy.jd2.controllers.advice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import org.example.mylib.tm.itacademy.enums.ErrorType;
import org.example.mylib.tm.itacademy.errors.ErrorResponse;
import org.example.mylib.tm.itacademy.errors.StructuredErrorResponse;
import org.example.mylib.tm.itacademy.exceptions.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
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
public class TaskExceptionHandler {
    private static final String INCORRECT_QUERY_CHARACTERS = "The characters in the query were entered incorrectly. Change the request and try again.";
    private static final String INTERNAL_SERVER_ERROR = "An internal server error has occurred. Please contact support.";

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

    @ExceptionHandler({CustomValidationException.class})
    public ResponseEntity<StructuredErrorResponse> invalidFieldException(
            CustomValidationException ex) {

        StructuredErrorResponse response = new StructuredErrorResponse(
                ErrorType.STRUCTURED_ERROR, new HashMap<>());

        response.setErrorMap(ex.getErrors());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StructuredErrorResponse> handleJsonErrors(HttpMessageNotReadableException ex) {

        StructuredErrorResponse response = new StructuredErrorResponse(
                ErrorType.STRUCTURED_ERROR, new HashMap<>());

        response.getErrorMap().put(ex.getCause().toString(), ex.getMessage());

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<List<ErrorResponse>> handleAuthenticationException(AuthenticationException ex) {
        List<ErrorResponse> errorList = new ArrayList<>();
        ErrorResponse error = new ErrorResponse(
                ErrorType.ERROR, ex.getMessage());
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
            response.getErrorMap().put(fieldName, error.getDefaultMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<List<ErrorResponse>> handleIllegalStateException(IllegalStateException ex) {
        List<ErrorResponse> errorList = new ArrayList<>();
        ErrorResponse error = new ErrorResponse(
                ErrorType.ERROR, ex.getMessage());
        errorList.add(error);

        return new ResponseEntity(errorList, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotCorrectValueException.class)
    public ResponseEntity<List<ErrorResponse>> handleNotCorrectValueException(NotCorrectValueException ex) {
        return new ResponseEntity(ex.getValues(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<List<ErrorResponse>> handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        List<ErrorResponse> errorList = new ArrayList<>();
        errorList.add(new ErrorResponse(ErrorType.ERROR, INCORRECT_QUERY_CHARACTERS));

        return new ResponseEntity(errorList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<List<ErrorResponse>> handleJwtException(ExpiredJwtException ex) {
        List<ErrorResponse> errorList = new ArrayList<>();
        errorList.add(new ErrorResponse(ErrorType.ERROR, ex.getCause().getMessage()));

        return new ResponseEntity(errorList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<List<ErrorResponse>> handleSignatureException(SignatureException ex) {
        List<ErrorResponse> errorList = new ArrayList<>();
        errorList.add(new ErrorResponse(ErrorType.ERROR, ex.getCause().getMessage()));

        return new ResponseEntity(errorList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<List<ErrorResponse>> handleSignatureException(MalformedJwtException ex) {
        List<ErrorResponse> errorList = new ArrayList<>();
        errorList.add(new ErrorResponse(ErrorType.ERROR, ex.getCause().getMessage()));

        return new ResponseEntity(errorList, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<List<ErrorResponse>> authenticationException(AccessDeniedException ex) {

        List<ErrorResponse> errorList = new ArrayList<>();
        errorList.add(new ErrorResponse(ErrorType.ERROR, ex.getCause().getMessage()));

        return new ResponseEntity(errorList, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UpdateEntityException.class)
    public ResponseEntity<List<ErrorResponse>> handleUpdateEntityException(UpdateEntityException ex) {

        List<ErrorResponse> errorList = new ArrayList<>();
        errorList.add(new ErrorResponse(ErrorType.ERROR, ex.getMessage()));

        return new ResponseEntity(errorList, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({IllegalArgumentException.class,
            IOException.class,
            IndexOutOfBoundsException.class,
            ArithmeticException.class,
            Error.class,
    })
    public ResponseEntity<List<ErrorResponse>> handleInnerError(Exception ex) {
        List<ErrorResponse> errorList = new ArrayList<>();
        ErrorResponse error = new ErrorResponse(
                ErrorType.ERROR, INTERNAL_SERVER_ERROR);
        errorList.add(error);

        return new ResponseEntity<>(errorList, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UniqueConstraintViolation.class})
    public ResponseEntity<StructuredErrorResponse> handleInvalidArgument(
            UniqueConstraintViolation ex) {

        StructuredErrorResponse response = new StructuredErrorResponse(
                ErrorType.STRUCTURED_ERROR, new HashMap<>());

        StackTraceElement[] stackTrace = ex.getStackTrace();
        String methodName = stackTrace[0].getMethodName() + ex.getMessageField();
        response.getErrorMap().put(methodName, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResultNotFoundException.class})
    public ResponseEntity<List<ErrorResponse>> handleResultError(ResultNotFoundException ex) {
        List<ErrorResponse> errorList = new ArrayList<>();
        ErrorResponse error = new ErrorResponse(
                ErrorType.ERROR, ex.getMessage());
        errorList.add(error);

        return new ResponseEntity<>(errorList, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<List<ErrorResponse>> handleNotFoundEntityException(EntityNotFoundException ex) {

        List<ErrorResponse> errorList = new ArrayList<>();
        errorList.add(new ErrorResponse(ErrorType.ERROR, ex.getMessage()));

        return new ResponseEntity(errorList, HttpStatus.NOT_FOUND);
    }
}
