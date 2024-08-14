package com.altimetrik.online_shop.customer_service.customer_service.customer.exceptionHandler;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleInvalidInputException() {
        // Given
        InvalidInputException exception = new InvalidInputException("Invalid input");

        // When
        ResponseEntity<String> response = exceptionHandler.handleInvalidInputException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input", response.getBody());
    }

    @Test
    public void testHandleResourceNotFoundException() {
        // Given
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        // When
        ResponseEntity<String> response = exceptionHandler.handleResourceNotFoundException(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", response.getBody());
    }

    @Test
    public void testHandleGeneralException() {
        // Given
        Exception exception = new Exception("Unexpected error");

        // When
        ResponseEntity<String> response = exceptionHandler.handleGeneralException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred. Please try again later.", response.getBody());
    }
}
