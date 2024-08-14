package com.altimetrik.online_shop.customer_service.customer_service.customer.exceptionHandler;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

