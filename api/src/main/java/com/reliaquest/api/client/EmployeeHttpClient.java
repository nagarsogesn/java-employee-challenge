package com.reliaquest.api.client;

import com.reliaquest.api.exception.InternalServerErrorException;
import com.reliaquest.api.model.AllEmployeeHttpResponse;
import com.reliaquest.api.model.CreateEmployeeInput;
import com.reliaquest.api.model.DeleteEmployeeHttpResponse;
import com.reliaquest.api.model.DeleteEmployeeInput;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeHttpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.reliaquest.api.util.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

/**
 * This class is responsible for making HTTP calls to the employee service for getting, creating and deleting employees.
 */
@Component
@Slf4j
public class EmployeeHttpClient {
    private final RestTemplate restTemplate;
    private final String url;

    public EmployeeHttpClient(RestTemplate restTemplate,
                              @Value("${employee.v1.server.host}") String host,
                              @Value("${employee.v1.server.path}") String path) {
        this.restTemplate = restTemplate;
        url = host + path;
    }

    /**
     * Fetches all employees from the employee service.
     * @return List of employees
     * @throws InternalServerErrorException if there is an error while fetching employees
     */
    public List<Employee> getAllEmployees() throws InternalServerErrorException {
        log.info("Fetching employees.");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResponseEntity<AllEmployeeHttpResponse> response = restTemplate
            .exchange(url, HttpMethod.GET, null, AllEmployeeHttpResponse.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            log.error("Error while getting employees. httpStatusCode={} responseBody={}",
                response.getStatusCode(), response.getBody());
            throw new InternalServerErrorException("Failed to get employees.");
        }

        AllEmployeeHttpResponse allEmployeeHttpResponse = response.getBody();
        if (allEmployeeHttpResponse == null)  {
            log.info("No employees found.");
            return new ArrayList<>();
        }

        List<Employee> employees = allEmployeeHttpResponse.getData();
        stopWatch.stop();
        log.info("Fetched employees. size={} timeTaken={}", employees.size(), stopWatch.getTotalTimeMillis());
        return employees;
    }

    /**
     * Fetches employee by id.
     * @param id employee id
     * @return employee if found, empty optional otherwise
     * @throws InternalServerErrorException if there is an error while fetching employee
     */
    public Optional<Employee> getEmployeeById(String id) throws InternalServerErrorException {
        log.info("Fetching employee by id={}", id);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResponseEntity<EmployeeHttpResponse> response =
            restTemplate.exchange(
                url + "/" + id,
                HttpMethod.GET,
                null,
                EmployeeHttpResponse.class
            );
        stopWatch.stop();
        log.info("Fetched employee by id={} timeTaken={}", id, stopWatch.getTotalTimeMillis());
        if (isInvalidResponse(response)) {
            log.error("Error while getting employee. httpStatusCode={} responseBody={}",
                response.getStatusCode(), response.getBody());
            throw new InternalServerErrorException("Failed to get employee.");
        }

        if (Objects.isNull(response.getBody().getData())) {
            log.info("No employee found with id={}", id);
            return Optional.empty();
        }

        return Optional.of(response.getBody().getData());
    }

    private static boolean isInvalidResponse(ResponseEntity<EmployeeHttpResponse> response) {
        return (response.getStatusCode() != HttpStatus.OK && response.getStatusCode() != HttpStatus.NOT_FOUND)
            || Objects.isNull(response.getBody());
    }

    /**
     * Creates an employee.
     * @param employeeInput employee input
     * @return created employee
     * @throws InternalServerErrorException if there is an error while creating employee
     */
    public Employee createEmployee(CreateEmployeeInput employeeInput) throws InternalServerErrorException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResponseEntity<EmployeeHttpResponse> response = restTemplate
            .exchange(url, HttpMethod.POST, new HttpEntity<>(employeeInput), EmployeeHttpResponse.class);
        stopWatch.stop();

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            log.error("Error while creating employee. httpStatusCode={} responseBody={} timeTaken={}",
                response.getStatusCode(), response.getBody().toString(), stopWatch.getTotalTimeMillis());
            throw new InternalServerErrorException("Failed to create employee.");
        }

        return response.getBody().getData();
    }

    /**
     * Deletes an employee.
     * @param deleteEmployeeInput delete employee input
     * @return true if an employee is deleted, false otherwise
     * @throws InternalServerErrorException if there is an error while deleting employee
     */
    public Optional<Boolean> deleteEmployee(DeleteEmployeeInput deleteEmployeeInput) throws InternalServerErrorException {
        log.info("Deleting employee with name={}", ApiUtil.mask(deleteEmployeeInput.getName()));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResponseEntity<DeleteEmployeeHttpResponse> response = restTemplate.exchange(
            url,
            HttpMethod.DELETE,
            new HttpEntity<>(deleteEmployeeInput),
            DeleteEmployeeHttpResponse.class
        );

        stopWatch.stop();

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            log.error("Error while deleting employee. httpStatusCode={} responseBody={} timeTaken={}",
                response.getStatusCode(), response.getBody(), stopWatch.getTotalTimeMillis());
            throw new InternalServerErrorException("Failed to delete employee.");
        }
        log.info("Deleted employee with name={} timeTaken={}",
            ApiUtil.mask(deleteEmployeeInput.getName()), stopWatch.getTotalTimeMillis());

        return Optional.of(response.getBody().isData());
    }
}
