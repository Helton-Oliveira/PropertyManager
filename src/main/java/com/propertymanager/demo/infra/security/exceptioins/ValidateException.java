package com.propertymanager.demo.infra.security.exceptioins;

public class ValidateException extends RuntimeException{
    public ValidateException(String message) {
        super(message);
    }
}
