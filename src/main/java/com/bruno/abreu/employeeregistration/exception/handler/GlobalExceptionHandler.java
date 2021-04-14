package com.bruno.abreu.employeeregistration.exception.handler;

import com.bruno.abreu.employeeregistration.exception.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status_code", HttpStatus.BAD_REQUEST.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "")
                .sorted()
                .collect(Collectors.toList());

        body.put("message", errors.toString());

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({
            EmployeeAlreadyRegisteredException.class,
            EmployeeExceedsLimitUnder18Exception.class,
            EmployeeExceedsLimitOver65Exception.class,
            EmployeeBlacklistedException.class,
            BlacklistApiException.class})
    public ResponseEntity<Object> handleEmployeeExceptions(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status_code", HttpStatus.BAD_REQUEST.value());
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status_code", HttpStatus.BAD_REQUEST.value());
        body.put("message", "Invalid sector!");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity handleEmptyResultDataAccessException() {
        return ResponseEntity.notFound().build();
    }
}
