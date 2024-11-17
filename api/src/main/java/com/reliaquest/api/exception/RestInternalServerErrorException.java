package com.reliaquest.api.exception;

// Keeping it runtime exception
// as updating IEmployeeController is not allowed
public class RestInternalServerErrorException extends RuntimeException {

    public RestInternalServerErrorException(String message) {
        super(message);
    }
}
