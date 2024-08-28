package com.propertymanager.demo.infra.exceptioins;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolation(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccressDenied() {
        return ResponseEntity.badRequest().body("Acesso negado");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupported() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Método HTTP não suportado.");
    }

    public record ExeptionData(String field, String message) {
        public ExeptionData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(CustomTokenInvalidException.class)
    public ResponseEntity<Object> handleAuthenticationException(CustomTokenInvalidException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
