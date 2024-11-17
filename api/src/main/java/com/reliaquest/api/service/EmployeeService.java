package com.reliaquest.api.service;

import com.reliaquest.api.client.EmployeeHttpClient;
import com.reliaquest.api.exception.InternalServerErrorException;
import com.reliaquest.api.model.CreateEmployeeInput;
import com.reliaquest.api.model.DeleteEmployeeInput;
import com.reliaquest.api.model.Employee;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class for Employee API requests
 */
@Service
@Slf4j
public class EmployeeService implements IEmployeeService {

    private final EmployeeHttpClient employeeHttpClient;

    public EmployeeService(EmployeeHttpClient employeeHttpClient) {
        this.employeeHttpClient = employeeHttpClient;
    }

    /**
     * Get all employees.
     * @return List of employees
     */
    @Override
    public List<Employee> getAllEmployees() throws InternalServerErrorException {
        return employeeHttpClient.getAllEmployees();
    }

    /**
     * Get employees by name search.
     * @param name Name to search for
     * @return List of employees
     */
    @Override
    public List<Employee> getEmployeesByNameSearch(String name) throws InternalServerErrorException {
        log.info("Searching for employees with name containing: {}", name);
        List<Employee> searchedEmployees = employeeHttpClient.getAllEmployees().stream()
            .filter(employee -> employee.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
        log.info("Found {} employees with name containing: {}", searchedEmployees.size(), name);
        return searchedEmployees;
    }

    /**
     * Get employee by ID.
     * @param id ID of employee
     * @return Employee
     */
    @Override
    public Optional<Employee> getEmployeeById(String id) throws InternalServerErrorException {
        return employeeHttpClient.getEmployeeById(id);
    }

    /**
     * Get the highest salary of employees.
     * @return Highest salary
     */
    @Override
    public Integer getHighestSalaryOfEmployees() throws InternalServerErrorException {
        return employeeHttpClient.getAllEmployees().stream()
                .map(Employee::getSalary)
                .max(Integer::compareTo)
                .orElse(0);
    }

    /**
     * Get the top ten highest earning employees.
     * @return List of employee names with the highest salaries
     */
    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() throws InternalServerErrorException {
        return employeeHttpClient.getAllEmployees().stream()
                .sorted((e1, e2) -> e2.getSalary().compareTo(e1.getSalary()))
                .limit(10)
                .map(e -> e.getName() + " (" + e.getSalary() + ")")
                .collect(Collectors.toList());
    }

    /**
     * Create an employee.
     * @param employeeInput Employee input
     * @return Created employee
     */
    @Override
    public Employee createEmployee(CreateEmployeeInput employeeInput) throws InternalServerErrorException {
        return employeeHttpClient.createEmployee(employeeInput);
    }

    /**
     * Delete an employee by ID.
     * @param id ID of employee
     * @return message
     */
    @Override
    public String deleteEmployeeById(String id) throws InternalServerErrorException {
        Optional<Employee> employee = employeeHttpClient.getEmployeeById(id);
        if (employee.isEmpty()) {
            return "Employee not found";
        }

        DeleteEmployeeInput deleteEmployeeInput = new DeleteEmployeeInput();
        deleteEmployeeInput.setName(employee.get().getName());

        Optional<Boolean> isDeleted = employeeHttpClient.deleteEmployee(deleteEmployeeInput);
        if (isDeleted.isEmpty()) {
            throw new InternalServerErrorException("Failed to delete employee");
        }

        return "Successfully deleted employee";
    }
}
