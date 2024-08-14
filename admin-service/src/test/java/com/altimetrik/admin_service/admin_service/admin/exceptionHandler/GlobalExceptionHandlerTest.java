package com.altimetrik.admin_service.admin_service.admin.exceptionHandler;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleInvalidInputException() {
        // Given
        InvalidInputException exception = new InvalidInputException("Invalid input provided");

        // When
        ResponseEntity<String> response = exceptionHandler.handleInvalidInputException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input provided", response.getBody());
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
        Exception exception = new Exception("Some unexpected error");

        // When
        ResponseEntity<String> response = exceptionHandler.handleGeneralException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred. Please try again later.", response.getBody());
    }

    @Test
    public void testHandleMissingServletRequestParameterException() {
        // Given
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException("param", "type");

        // When
        ResponseEntity<String> response = exceptionHandler.handleGeneralException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred. Please try again later.", response.getBody());
    }
}
