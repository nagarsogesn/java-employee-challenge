package com.reliaquest.api.util;

import com.reliaquest.api.constants.ErrorMessages;
import com.reliaquest.api.model.CreateEmployeeInput;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.UUID;

/**
 * Utility class for holding common utility methods for API validation.
 */
public class ApiValidationUtil {

    /**
     * Validates the employee ID. It should not be null or empty and should be a valid UUID.
     * @param id Employee ID
     */
    public static void validateEmployeeId(String id) {
        if (Strings.isBlank(id)) {
            throw new IllegalArgumentException(ErrorMessages.EMPLOYEE_ID_NULL_OR_EMPTY);
        }

        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(ErrorMessages.INVALID_EMPLOYEE_ID, id));
        }
    }

    /**
     * Validates the create employee input. It should not be null and should have valid values.
     * @param employeeInput CreateEmployeeInput
     */
    public static void validateCreateEmployeeInput(CreateEmployeeInput employeeInput) {
        if (employeeInput == null) {
            throw new IllegalArgumentException(ErrorMessages.EMPLOYEE_INPUT_NULL);
        }

        if (Strings.isBlank(employeeInput.getName())) {
            throw new IllegalArgumentException(ErrorMessages.EMPLOYEE_NAME_NULL_OR_EMPTY);
        }

        if (employeeInput.getSalary() == null || employeeInput.getSalary() < 0) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_EMPLOYEE_SALARY);
        }

        if (employeeInput.getAge() == null || employeeInput.getAge() < 16 || employeeInput.getAge() > 75) {
            throw new IllegalArgumentException(String.format(ErrorMessages.INVALID_EMPLOYEE_AGE, employeeInput.getAge()));
        }

        if (Strings.isBlank(employeeInput.getTitle())) {
            throw new IllegalArgumentException(ErrorMessages.EMPLOYEE_TITLE_NULL_OR_EMPTY);
        }
    }

    /**
     * Validates the search request. It should not be null or empty.
     * @param name Name to search for
     */
    public static void validateSearchRequest(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException(ErrorMessages.EMPLOYEE_NAME_NULL_OR_EMPTY);
        }
    }
}
