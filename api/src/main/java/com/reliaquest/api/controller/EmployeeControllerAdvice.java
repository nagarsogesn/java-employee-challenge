package com.reliaquest.api.controller;

import com.reliaquest.api.exception.RestBadRequestException;
import com.reliaquest.api.exception.RestInternalServerErrorException;
import com.reliaquest.api.exception.RestNotFoundRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@ControllerAdvice
public class EmployeeControllerAdvice {

    @ExceptionHandler
    protected ResponseEntity<?> handleException(Throwable ex) {
        if (ex instanceof RestBadRequestException) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (ex instanceof RestInternalServerErrorException) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ex instanceof RestNotFoundRequestException) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (ex instanceof HttpClientErrorException) { // this can be fine-tuned further
            return new ResponseEntity<>(ex.getMessage(), ((HttpClientErrorException) ex).getStatusCode());
        }

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
