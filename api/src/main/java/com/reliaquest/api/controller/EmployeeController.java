package com.reliaquest.api.controller;

import com.reliaquest.api.exception.RestBadRequestException;
import com.reliaquest.api.exception.RestInternalServerErrorException;
import com.reliaquest.api.exception.RestNotFoundRequestException;
import com.reliaquest.api.model.CreateEmployeeInput;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.IEmployeeService;
import java.util.List;
import java.util.Optional;

import com.reliaquest.api.util.ApiValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Controller class for Employee API
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController implements IEmployeeController<Employee, CreateEmployeeInput> {

    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Get all employees.
     * @return List of employees
     */
    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            return ResponseEntity.ok(employeeService.getAllEmployees());
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while getting employees.", e);
            throw new RestInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Get employees by searchString search.
     * @param searchString Name to search for
     * @return List of employees
     */
    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        try {
            ApiValidationUtil.validateSearchRequest(searchString);
            return ResponseEntity.ok(employeeService.getEmployeesByNameSearch(searchString));
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while searching for employees with searchString containing: {}", searchString, e);
            throw new RestInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Get employee by id.
     * @param id Employee id
     * @return Employee
     */
    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        try {
            Optional<Employee> employeeById = employeeService.getEmployeeById(id);
            if (employeeById.isEmpty()) {
                throw new RestNotFoundRequestException("Employee id not found: " + id);
            }

            return ResponseEntity.ok(employeeById.get());
        }  catch (HttpClientErrorException e) {
            log.error("Error while getting employee by id: {}", id, e);
            if (e.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
                throw new RestNotFoundRequestException("Employee id not found: " + id);
            }
            throw new RestInternalServerErrorException(e.getMessage());
        } catch (RestNotFoundRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while getting employee by id: {}", id, e);
            throw new RestInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Get the highest salary of employees.
     * @return Highest salary
     */
    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        try {
            return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while getting highest salary of employees.", e);
            throw new RestInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Get the top ten highest earning employee names.
     * @return List of employee names
     */
    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        try {
            return ResponseEntity.ok(employeeService.getTopTenHighestEarningEmployeeNames());
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while getting top ten highest earning employee names.", e);
            throw new RestInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Create an employee.
     * @param employeeInput Employee input
     * @return Created employee
     */
    @Override
    public ResponseEntity<Employee> createEmployee(CreateEmployeeInput employeeInput) {
        try {
            ApiValidationUtil.validateCreateEmployeeInput(employeeInput);
            return ResponseEntity.ok(employeeService.createEmployee(employeeInput));
        } catch (IllegalArgumentException e) {
            log.error("Error while creating employee: {}", employeeInput, e);
            throw new RestBadRequestException(e.getMessage());
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while creating employee: {}", employeeInput, e);
            throw new RestInternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Delete an employee by id.
     * @param id Employee id
     * @return message
     */
    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        try {
            ApiValidationUtil.validateEmployeeId(id);
            return ResponseEntity.ok(employeeService.deleteEmployeeById(id));
        } catch (IllegalArgumentException e) {
            log.error("Error while deleting employee by id: {}", id, e);
            throw new RestBadRequestException(e.getMessage());
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error while deleting employee by id: {}", id, e);
            throw new RestInternalServerErrorException(e.getMessage());
        }
    }
}
