package com.bruno.abreu.employeeregistration.service;

import com.bruno.abreu.employeeregistration.exception.EmployeeAlreadyRegisteredException;
import com.bruno.abreu.employeeregistration.exception.EmployeeBlacklistedException;
import com.bruno.abreu.employeeregistration.exception.EmployeeExceedsLimitOver65Exception;
import com.bruno.abreu.employeeregistration.exception.EmployeeExceedsLimitUnder18Exception;
import com.bruno.abreu.employeeregistration.model.Employee;
import com.bruno.abreu.employeeregistration.model.Sector;
import com.bruno.abreu.employeeregistration.repository.EmployeeRepository;
import com.bruno.abreu.employeeregistration.service.business.EmployeeBusinessService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeServiceTest {

    private static Employee employee;
    @Autowired
    Service<Employee> employeeService;
    @MockBean
    EmployeeRepository employeeRepository;
    @MockBean
    EmployeeBusinessService employeeBusinessService;

    @BeforeAll
    static void setup() {
        employee = Employee.builder()
                .birthDate(LocalDate.now().minusYears(50))
                .build();
    }

    @Test
    void insertEmployeeShouldReturnEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee newEmployee = employeeService.insert(employee);

        assertEquals(employee, newEmployee);
    }

    @Test
    void insertEmployeeAlreadyRegisteredInAnotherSectorShouldReturnError() {
        when(employeeRepository.save(employee)).thenThrow(new EmployeeAlreadyRegisteredException());

        Exception exception = assertThrows(EmployeeAlreadyRegisteredException.class, () -> employeeService.insert(employee));

        String expectedMessage = "Employee is already registered!";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void insertEmployeeUnder18WhenExceedsSectorLimitShouldReturnError() {
        Employee employeeUnder18 = Employee.builder()
                .birthDate(LocalDate.now().minusYears(17))
                .sector(Sector.builder()
                        .id(1L)
                        .build())
                .build();

        when(employeeBusinessService.hasSectorLimitForEmployeesUnder18BeenExceeded(employeeUnder18)).thenReturn(true);

        Exception exception = assertThrows(EmployeeExceedsLimitUnder18Exception.class, () -> employeeService.insert(employeeUnder18));

        String expectedMessage = "Employee exceeds the limit of employees under 18!";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void insertEmployeeOver65WhenExceedsSectorLimitShouldReturnError() {
        Employee employeeOver65 = Employee.builder()
                .birthDate(LocalDate.now().minusYears(66))
                .sector(Sector.builder()
                        .id(1L)
                        .build())
                .build();

        when(employeeBusinessService.hasSectorLimitForEmployeesOver65BeenExceeded()).thenReturn(true);

        Exception exception = assertThrows(EmployeeExceedsLimitOver65Exception.class, () -> employeeService.insert(employeeOver65));

        String expectedMessage = "Employee exceeds the limit of employees over 65!";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void insertEmployeeThatExistOnBlacklistShouldReturnError() throws IOException, InterruptedException {
        Employee employeeOver65 = Employee.builder()
                .birthDate(LocalDate.now().minusYears(66))
                .sector(Sector.builder()
                        .id(1L)
                        .build())
                .build();

        when(employeeBusinessService.isBlacklisted(employeeOver65)).thenReturn(true);

        Exception exception = assertThrows(EmployeeBlacklistedException.class, () -> employeeService.insert(employeeOver65));

        String expectedMessage = "Employee is blacklisted!";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }
}
