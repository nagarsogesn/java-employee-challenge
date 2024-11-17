package com.reliaquest.api.exception;

/**
 * Exception for internal server error
 */
public class InternalServerErrorException extends Exception {
    public InternalServerErrorException(String err) {
        super(err);
    }
}
