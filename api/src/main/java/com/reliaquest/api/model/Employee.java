package com.reliaquest.api.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class for an employee
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Employee {
    private UUID id;

    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("employee_salary")
    private Integer salary;
    @JsonProperty("employee_age")
    private Integer age;
    @JsonProperty("employee_title")
    private String title;
    @JsonProperty("employee_email")
    private String email;
}
