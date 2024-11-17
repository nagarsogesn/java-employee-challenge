package com.reliaquest.api.exception;

// Keeping it runtime exception
// as updating IEmployeeController is not allowed
public class RestNotFoundRequestException extends RuntimeException {
    public RestNotFoundRequestException(String message) {
        super(message);
    }
}
