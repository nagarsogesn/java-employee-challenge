package com.reliaquest.api.client;

import com.reliaquest.api.exception.InternalServerErrorException;
import com.reliaquest.api.model.AllEmployeeHttpResponse;
import com.reliaquest.api.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeHttpClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeHttpClient employeeHttpClient;

    @Test
    void getAllEmployees_Success() throws InternalServerErrorException {
        AllEmployeeHttpResponse allEmployeeHttpResponse = new AllEmployeeHttpResponse();
        Employee employee = new Employee();
        UUID empId = UUID.randomUUID();
        employee.setId(empId);
        allEmployeeHttpResponse.setData(List.of(employee));
        ResponseEntity<AllEmployeeHttpResponse> empployeesExpected = ResponseEntity.ok(allEmployeeHttpResponse);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.eq(null), Mockito.eq(AllEmployeeHttpResponse.class)))
                .thenReturn(empployeesExpected);
        List<Employee> employeesActual = employeeHttpClient.getAllEmployees();
        assert (employeesActual.size() == 1);
        assert (employeesActual.get(0).getId().equals(empId));
    }

    @Test
    void getAllEmployees_Failure() {
        ResponseEntity<AllEmployeeHttpResponse> empployeesExpected = ResponseEntity.badRequest().build();
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.eq(null), Mockito.eq(AllEmployeeHttpResponse.class)))
                .thenReturn(empployeesExpected);
        assertThrows(InternalServerErrorException.class, () -> employeeHttpClient.getAllEmployees());
    }

    // More tests can be added here
}