package com.reliaquest.api.constants;

/**
 * Error messages returned in the API response can be maintained here.
 */
public interface ErrorMessages {
    String EMPLOYEE_ID_NULL_OR_EMPTY = "Employee id cannot be null or empty";
    String INVALID_EMPLOYEE_ID = "Invalid employee id: %s. It should be a valid UUID.";
    String EMPLOYEE_INPUT_NULL = "Employee input cannot be null";
    String EMPLOYEE_NAME_NULL_OR_EMPTY = "Employee name cannot be null or empty";
    String INVALID_EMPLOYEE_SALARY = "Employee salary cannot be null or negative";
    String INVALID_EMPLOYEE_AGE = "Invalid employee age: %s. It should be between 16 and 75.";
    String EMPLOYEE_TITLE_NULL_OR_EMPTY = "Employee title cannot be null or empty";
}
