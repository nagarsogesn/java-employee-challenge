package com.reliaquest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for creating an employee
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeInput {
    private String name;
    private Integer salary;
    private Integer age;
    private String title;
}
