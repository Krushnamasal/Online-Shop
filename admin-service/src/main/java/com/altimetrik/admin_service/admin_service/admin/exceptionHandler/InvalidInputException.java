package com.altimetrik.admin_service.admin_service.admin.exceptionHandler;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
