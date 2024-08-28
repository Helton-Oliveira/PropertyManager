package com.propertymanager.demo.infra.exceptioins;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomTokenInvalidException extends RuntimeException{
    public CustomTokenInvalidException(String message) {
        super(message);
    }

    public CustomTokenInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
