package com.reliaquest.api.controller;

import com.reliaquest.api.exception.InternalServerErrorException;
import com.reliaquest.api.exception.RestBadRequestException;
import com.reliaquest.api.exception.RestInternalServerErrorException;
import com.reliaquest.api.exception.RestNotFoundRequestException;
import com.reliaquest.api.model.CreateEmployeeInput;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    public void testGetAllEmployees_Success() throws InternalServerErrorException {
        List<Employee> employeesExpected = new ArrayList<>();
        Employee employee = new Employee();
        UUID employeeId = UUID.randomUUID();
        employee.setId(employeeId);
        String employeeName = "Test employee name";
        employee.setName(employeeName);
        Integer employeeAge = 30;
        employee.setAge(employeeAge);
        String employeeEmail = "email@company.com";
        employee.setEmail(employeeEmail);
        employeesExpected.add(employee);
        when(employeeService.getAllEmployees()).thenReturn(employeesExpected);
        ResponseEntity<List<Employee>> employeesActual = employeeController.getAllEmployees();
        List<Employee> employeeList = employeesActual.getBody();
        assert employeeList != null;
        assert employeeList.size() == 1;
        assert employeeList.get(0).getId().equals(employeeId);
        assert employeeList.get(0).getName().equals(employeeName);
        assert employeeList.get(0).getAge().equals(employeeAge);
        assert employeeList.get(0).getEmail().equals(employeeEmail);
    }

    @Test
    public void testGetAllEmployees_Failure() throws InternalServerErrorException {
        when(employeeService.getAllEmployees()).thenThrow(new InternalServerErrorException("Failed to get employees."));
        assertThrows(RestInternalServerErrorException.class, () -> employeeController.getAllEmployees());
    }

    @Test
    public void testGetEmployeesByNameSearch_Success() throws InternalServerErrorException {
        List<Employee> employeesExpected = new ArrayList<>();
        Employee employee = new Employee();
        UUID employeeId = UUID.randomUUID();
        employee.setId(employeeId);
        String employeeName = "Test employee name";
        employee.setName(employeeName);
        Integer employeeAge = 30;
        employee.setAge(employeeAge);
        String employeeEmail = "email@company.com";
        employee.setEmail(employeeEmail);
        employeesExpected.add(employee);
        when(employeeService.getEmployeesByNameSearch(employeeName)).thenReturn(employeesExpected);
        ResponseEntity<List<Employee>> employeesActual = employeeController.getEmployeesByNameSearch(employeeName);
        List<Employee> employeeList = employeesActual.getBody();
        assert employeeList != null;
        assert employeeList.size() == 1;
        assert employeeList.get(0).getId().equals(employeeId);
        assert employeeList.get(0).getName().equals(employeeName);
        assert employeeList.get(0).getAge().equals(employeeAge);
        assert employeeList.get(0).getEmail().equals(employeeEmail);
    }

    @Test
    public void testGetEmployeesByNameSearch_Failure() throws InternalServerErrorException {
        String employeeName = "Test employee name";
        when(employeeService.getEmployeesByNameSearch(employeeName)).thenThrow(new InternalServerErrorException("Failed to get employees."));
        assertThrows(RestInternalServerErrorException.class, () -> employeeController.getEmployeesByNameSearch(employeeName));
    }

    @Test
    public void testGetEmployeeById_Success() throws InternalServerErrorException {
        Employee employee = new Employee();
        UUID employeeId = UUID.randomUUID();
        employee.setId(employeeId);
        String employeeName = "Test employee name";
        employee.setName(employeeName);
        Integer employeeAge = 30;
        employee.setAge(employeeAge);
        String employeeEmail = "email@company.com";
        employee.setEmail(employeeEmail);
        when(employeeService.getEmployeeById(employeeId.toString())).thenReturn(java.util.Optional.of(employee));
        ResponseEntity<Employee> employeeActual = employeeController.getEmployeeById(employeeId.toString());
        Employee employeeResult = employeeActual.getBody();
        assert employeeResult != null;
        assert employeeResult.getId().equals(employeeId);
        assert employeeResult.getName().equals(employeeName);
        assert employeeResult.getAge().equals(employeeAge);
        assert employeeResult.getEmail().equals(employeeEmail);
    }

    @Test
    public void testGetEmployeeById_Failure() throws InternalServerErrorException {
        UUID employeeId = UUID.randomUUID();
        when(employeeService.getEmployeeById(employeeId.toString())).thenThrow(new InternalServerErrorException("Failed to get employees."));
        assertThrows(RestInternalServerErrorException.class, () -> employeeController.getEmployeeById(employeeId.toString()));
    }

    @Test
    public void testGetHighestSalaryOfEmployees_Success() throws InternalServerErrorException {
        Integer highestSalary = 100000;
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(highestSalary);
        ResponseEntity<Integer> highestSalaryActual = employeeController.getHighestSalaryOfEmployees();
        Integer highestSalaryResult = highestSalaryActual.getBody();
        assert highestSalaryResult != null;
        assert highestSalaryResult.equals(highestSalary);
    }

    @Test
    public void testGetHighestSalaryOfEmployees_Failure() throws InternalServerErrorException {
        when(employeeService.getHighestSalaryOfEmployees()).thenThrow(new InternalServerErrorException("Failed to get employees."));
        assertThrows(RestInternalServerErrorException.class, () -> employeeController.getHighestSalaryOfEmployees());
    }

    @Test
    public void testGetTopTenHighestEarningEmployeeNames_Success() throws InternalServerErrorException {
        List<String> employeeNames = new ArrayList<>();
        employeeNames.add("Employee 1");
        employeeNames.add("Employee 2");
        employeeNames.add("Employee 3");
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(employeeNames);
        ResponseEntity<List<String>> employeeNamesActual = employeeController.getTopTenHighestEarningEmployeeNames();
        List<String> employeeNamesResult = employeeNamesActual.getBody();
        assert employeeNamesResult != null;
        assert employeeNamesResult.size() == 3;
        assert employeeNamesResult.get(0).equals("Employee 1");
        assert employeeNamesResult.get(1).equals("Employee 2");
        assert employeeNamesResult.get(2).equals("Employee 3");
    }

    @Test
    public void testGetTopTenHighestEarningEmployeeNames_Failure() throws InternalServerErrorException {
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenThrow(new InternalServerErrorException("Failed to get employees."));
        assertThrows(RestInternalServerErrorException.class, () -> employeeController.getTopTenHighestEarningEmployeeNames());
    }

    @Test
    public void testGetEmployeeById_NotFound() throws InternalServerErrorException {
        UUID employeeId = UUID.randomUUID();
        when(employeeService.getEmployeeById(employeeId.toString())).thenReturn(java.util.Optional.empty());
        assertThrows(RestNotFoundRequestException.class, () -> employeeController.getEmployeeById(employeeId.toString()));
    }

    @Test
    public void testGetEmployeeById_InternalServerError() throws InternalServerErrorException {
        UUID employeeId = UUID.randomUUID();
        when(employeeService.getEmployeeById(employeeId.toString())).thenThrow(new InternalServerErrorException("Failed to get employees."));
        assertThrows(RestInternalServerErrorException.class, () -> employeeController.getEmployeeById(employeeId.toString()));
    }

    @Test
    public void testCreateEmployee_Success() throws InternalServerErrorException {
        CreateEmployeeInput employeeInput = new CreateEmployeeInput();
        String employeeName = "Test employee name";
        employeeInput.setName(employeeName);
        Integer employeeAge = 30;
        employeeInput.setAge(employeeAge);
        employeeInput.setTitle("Test title");
        employeeInput.setSalary(123);
        String employeeEmail = "email@company.com";
        Employee employee = new Employee();
        UUID employeeId = UUID.randomUUID();
        employee.setId(employeeId);
        employee.setName(employeeName);
        employee.setAge(employeeAge);
        employee.setEmail(employeeEmail);
        when(employeeService.createEmployee(employeeInput)).thenReturn(employee);
        ResponseEntity<Employee> employeeActual = employeeController.createEmployee(employeeInput);
        Employee employeeResult = employeeActual.getBody();
        assert employeeResult != null;
        assert employeeResult.getId().equals(employeeId);
        assert employeeResult.getName().equals(employeeName);
        assert employeeResult.getAge().equals(employeeAge);
        assert employeeResult.getEmail().equals(employeeEmail);
    }

    @Test
    public void testCreateEmployee_BadRequest() {
        CreateEmployeeInput employeeInput = new CreateEmployeeInput();
        String employeeName = "Test employee name";
        employeeInput.setName(employeeName);
        Integer employeeAge = 30;
        employeeInput.setAge(employeeAge);
        assertThrows(RestBadRequestException.class, () -> employeeController.createEmployee(employeeInput));
    }
}