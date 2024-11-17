package com.reliaquest.api.util;

import com.reliaquest.api.model.CreateEmployeeInput;

public class TestDataUtil {
    public static CreateEmployeeInput createEmployeeInput() {
        return new CreateEmployeeInput("Some name", 1000, 25, "Software Engineer");
    }

    public static CreateEmployeeInput createEmployeeInput(String name, int salary, int age, String title) {
        return new CreateEmployeeInput(name, salary, age, title);
    }
}
