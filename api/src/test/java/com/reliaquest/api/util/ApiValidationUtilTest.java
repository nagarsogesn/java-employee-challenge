package com.reliaquest.api.util;

import com.reliaquest.api.constants.ErrorMessages;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ApiValidationUtilTest {

    @Test
    public void testValidateEmployeeId_ValidUUID() {
        Assertions.assertThatNoException().isThrownBy(() -> ApiValidationUtil.validateEmployeeId(UUID.randomUUID().toString()));
    }

    @Test
    public void testValidateEmployeeId_Null() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ApiValidationUtil.validateEmployeeId(null));
        assertEquals(ErrorMessages.EMPLOYEE_ID_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    public void testValidateEmployeeId_Empty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ApiValidationUtil.validateEmployeeId(""));
        assertEquals(ErrorMessages.EMPLOYEE_ID_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    public void testValidateEmployeeId_InvalidUUID() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ApiValidationUtil.validateEmployeeId("invalid-uuid"));
        assertEquals(String.format(ErrorMessages.INVALID_EMPLOYEE_ID, "invalid-uuid"), exception.getMessage());
    }

    @Test
    public void testValidateCreateEmployeeInput_ValidInput() {
        Assertions.assertThatNoException().isThrownBy(() -> ApiValidationUtil.validateCreateEmployeeInput(TestDataUtil.createEmployeeInput()));
    }

    @Test
    public void testValidateCreateEmployeeInput_Null() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> ApiValidationUtil.validateCreateEmployeeInput(null));
        assertEquals(ErrorMessages.EMPLOYEE_INPUT_NULL, exception.getMessage());
    }

    @Test
    public void testValidateCreateEmployeeInput_NullName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> ApiValidationUtil.validateCreateEmployeeInput(TestDataUtil.createEmployeeInput(null, 1000, 25, "Software Engineer")));
        assertEquals(ErrorMessages.EMPLOYEE_NAME_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    public void testValidateCreateEmployeeInput_NegativeSalary() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> ApiValidationUtil.validateCreateEmployeeInput(
                TestDataUtil.createEmployeeInput("My name", -1000, 25, "Software Engineer")));
        assertEquals(ErrorMessages.INVALID_EMPLOYEE_SALARY, exception.getMessage());
    }

    @Test
    public void testValidateCreateEmployeeInput_LowAge() {
        int age = 15;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> ApiValidationUtil.validateCreateEmployeeInput(
                TestDataUtil.createEmployeeInput("My name", 1000, age, "Software Engineer")));
        assertEquals(String.format(ErrorMessages.INVALID_EMPLOYEE_AGE, age), exception.getMessage());
    }

    @Test
    public void testValidateCreateEmployeeInput_HighAge() {
        int age = 76;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> ApiValidationUtil.validateCreateEmployeeInput(
                TestDataUtil.createEmployeeInput("My name", 1000, age, "Software Engineer")));
        assertEquals(String.format(ErrorMessages.INVALID_EMPLOYEE_AGE, age), exception.getMessage());
    }

    @Test
    public void testValidateCreateEmployeeInput_NullTitle() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> ApiValidationUtil.validateCreateEmployeeInput(
                TestDataUtil.createEmployeeInput("My name", 1000, 25, null)));
        assertEquals(ErrorMessages.EMPLOYEE_TITLE_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    public void testValidateSearchRequest_ValidName() {
        Assertions.assertThatNoException().isThrownBy(() -> ApiValidationUtil.validateSearchRequest("test name"));
    }

    @Test
    public void testValidateSearchRequest_NullName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> ApiValidationUtil.validateSearchRequest(null));
        assertEquals(ErrorMessages.EMPLOYEE_NAME_NULL_OR_EMPTY, exception.getMessage());
    }

    @Test
    public void testValidateSearchRequest_EmptyName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> ApiValidationUtil.validateSearchRequest(""));
        assertEquals(ErrorMessages.EMPLOYEE_NAME_NULL_OR_EMPTY, exception.getMessage());
    }
}