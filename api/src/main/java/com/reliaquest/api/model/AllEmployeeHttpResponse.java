package com.reliaquest.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Model class for deserializing the response for all employees
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllEmployeeHttpResponse {
    private List<Employee> data;
}
