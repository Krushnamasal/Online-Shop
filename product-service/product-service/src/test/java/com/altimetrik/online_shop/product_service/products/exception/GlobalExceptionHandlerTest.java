package com.altimetrik.online_shop.product_service.products.exception;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleMissingParams() {
        // Given
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException("param", "String");

        // When
        ResponseEntity<String> response = exceptionHandler.handleMissingParams(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("param parameter is missing", response.getBody());
    }

    @Test
    public void testHandleIllegalArgumentException() {
        // Given
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument provided");

        // When
        ResponseEntity<String> response = exceptionHandler.handleIllegalArgumentException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid argument provided", response.getBody());
    }

    @Test
    public void testHandleNoProductsFoundException() {
        // Given
        NoProductsFoundException exception = new NoProductsFoundException("No products found");

        // When
        ResponseEntity<String> response = exceptionHandler.handleNoProductsFoundException(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No products found", response.getBody());
    }

    @Test
    public void testHandleRuntimeException() {
        // Given
        RuntimeException exception = new RuntimeException("Unexpected error occurred");

        // When
        ResponseEntity<String> response = exceptionHandler.handleRuntimeException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error occurred", response.getBody());
    }
}
