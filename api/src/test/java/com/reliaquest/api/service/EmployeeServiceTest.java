package com.reliaquest.api.service;

import com.reliaquest.api.client.EmployeeHttpClient;
import com.reliaquest.api.exception.InternalServerErrorException;
import com.reliaquest.api.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeHttpClient employeeHttpClient;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testGetAllEmployees_Success() throws InternalServerErrorException {
        ArrayList<Employee> employees = new ArrayList<>();
        Mockito.when(employeeHttpClient.getAllEmployees()).thenReturn(employees);
        assertEquals(employees, employeeService.getAllEmployees());
    }

    @Test
    public void testGetAllEmployees_InternalServerError() throws InternalServerErrorException {
        Mockito.when(employeeHttpClient.getAllEmployees()).thenThrow(new InternalServerErrorException("Internal Server Error"));
        Assertions.assertThrows(InternalServerErrorException.class, () -> employeeService.getAllEmployees());
    }

    @Test
    public void testGetEmployeesByNameSearch_Success() throws InternalServerErrorException {
        ArrayList<Employee> employees = new ArrayList<>();
        Mockito.when(employeeHttpClient.getAllEmployees()).thenReturn(employees);
        assertEquals(employees, employeeService.getEmployeesByNameSearch("test"));
    }

    @Test
    public void testGetEmployeesByNameSearch_InternalServerError() throws InternalServerErrorException {
        Mockito.when(employeeHttpClient.getAllEmployees()).thenThrow(new InternalServerErrorException("Internal Server Error"));
        Assertions.assertThrows(InternalServerErrorException.class, () -> employeeService.getEmployeesByNameSearch("test"));
    }

    @Test
    public void testGetEmployeeById_Success() throws InternalServerErrorException {
        Employee employee = new Employee();
        Mockito.when(employeeHttpClient.getEmployeeById("1")).thenReturn(java.util.Optional.of(employee));
        assertEquals(java.util.Optional.of(employee), employeeService.getEmployeeById("1"));
    }

    @Test
    public void testGetEmployeeById_InternalServerError() throws InternalServerErrorException {
        Mockito.when(employeeHttpClient.getEmployeeById("1")).thenThrow(new InternalServerErrorException("Internal Server Error"));
        Assertions.assertThrows(InternalServerErrorException.class, () -> employeeService.getEmployeeById("1"));
    }

    @Test
    public void testGetEmployeeById_NotFound() throws InternalServerErrorException {
        Mockito.when(employeeHttpClient.getEmployeeById("1")).thenReturn(java.util.Optional.empty());
        assertEquals(java.util.Optional.empty(), employeeService.getEmployeeById("1"));
    }

    @Test
    public void testGetHighestSalaryOfEmployees_Success() throws InternalServerErrorException {
        ArrayList<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setSalary(100);
        employees.add(employee1);

        Employee employee2 = new Employee();
        employee2.setSalary(10);
        employees.add(employee2);
        Mockito.when(employeeHttpClient.getAllEmployees()).thenReturn(employees);
        assertEquals(100, employeeService.getHighestSalaryOfEmployees());
    }

    @Test
    public void testGetHighestSalaryOfEmployees_InternalServerError() throws InternalServerErrorException {
        Mockito.when(employeeHttpClient.getAllEmployees()).thenThrow(new InternalServerErrorException("Internal Server Error"));
        Assertions.assertThrows(InternalServerErrorException.class, () -> employeeService.getHighestSalaryOfEmployees());
    }

    @Test
    public void testGetHighestSalaryOfEmployees_NoEmployees() throws InternalServerErrorException {
        Mockito.when(employeeHttpClient.getAllEmployees()).thenReturn(new ArrayList<>());
        assertEquals(0, employeeService.getHighestSalaryOfEmployees());
    }

    // More tests can be added here
}