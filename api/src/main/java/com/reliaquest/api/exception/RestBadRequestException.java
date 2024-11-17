package com.reliaquest.api.exception;

/**
 * This exception is thrown when the request is not valid and can be generically used for returning 400 Bad Request.
 */
// Keeping it runtime exception
// as updating IEmployeeController is not allowed
public class RestBadRequestException extends RuntimeException {

    public RestBadRequestException(String message) {
        super(message);
    }
}
