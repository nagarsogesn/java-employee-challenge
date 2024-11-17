package com.reliaquest.api.service;

import com.reliaquest.api.exception.InternalServerErrorException;
import com.reliaquest.api.model.CreateEmployeeInput;
import com.reliaquest.api.model.Employee;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface IEmployeeService {
    List<Employee> getAllEmployees() throws InternalServerErrorException;

    List<Employee> getEmployeesByNameSearch(String name) throws InternalServerErrorException;

    Optional<Employee> getEmployeeById(String id) throws InternalServerErrorException;

    Integer getHighestSalaryOfEmployees() throws InternalServerErrorException;

    List<String> getTopTenHighestEarningEmployeeNames() throws InternalServerErrorException;

    Employee createEmployee(CreateEmployeeInput employeeInput) throws InternalServerErrorException;

    String deleteEmployeeById(String id) throws InternalServerErrorException;
}
