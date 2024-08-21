package com.propertymanager.demo.infra.exceptioins;

public class ValidateException extends RuntimeException{
    public ValidateException(String message) {
        super(message);
    }
}
