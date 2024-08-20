package com.propertymanager.demo.infra.security.exceptioins;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handle404Error() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleError400(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ExeptionData::new));
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity treatErrorBusinessRule(ValidateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    public record ExeptionData(String field, String message) {
        public ExeptionData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
