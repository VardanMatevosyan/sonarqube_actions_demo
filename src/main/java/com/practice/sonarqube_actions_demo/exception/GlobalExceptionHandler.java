package com.practice.sonarqube_actions_demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleException(NoSuchElementException exception) {
        HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value());
        ErrorResponse errorResponse = ErrorResponse.create(exception, httpStatusCode, exception.getMessage());
        return ResponseEntity.status(httpStatusCode).body(errorResponse);
    }

    @ExceptionHandler(value = {Exception.class, DaoException.class})
    public ResponseEntity<ErrorResponse> handleException(DaoException exception) {
        HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ErrorResponse errorResponse = ErrorResponse.create(exception, httpStatusCode, exception.getMessage());
        return ResponseEntity.status(httpStatusCode).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException exception) {
        HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = ErrorResponse.create(exception, httpStatusCode, exception.getMessage());
        return ResponseEntity.status(httpStatusCode).body(errorResponse);
    }


}
