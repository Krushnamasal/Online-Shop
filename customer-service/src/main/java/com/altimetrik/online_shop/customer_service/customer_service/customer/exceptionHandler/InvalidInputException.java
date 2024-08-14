package com.altimetrik.online_shop.customer_service.customer_service.customer.exceptionHandler;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}