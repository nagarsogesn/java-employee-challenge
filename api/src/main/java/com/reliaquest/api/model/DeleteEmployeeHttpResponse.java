package com.reliaquest.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class for deserializing the response for deleting an employee
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteEmployeeHttpResponse {
    private boolean data;
}
