package com.bruno.abreu.employeeregistration.repository;

import com.bruno.abreu.employeeregistration.model.Employee;
import com.bruno.abreu.employeeregistration.model.Sector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    private static Employee employee;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeAll
    static void setup() {
        employee = Employee.builder()
                .cpf("12345678900")
                .name("Employee 1")
                .phone("99999999999")
                .email("employe@employee.com")
                .birthDate(LocalDate.now())
                .sector(Sector.builder()
                        .id(1L)
                        .build())
                .build();
    }

    @Test
    void saveEmployeeShouldReturnEmployee() {
        Employee newEmployee = employeeRepository.save(employee);
        assertTrue(employeeRepository.findById(newEmployee.getCpf()).isPresent());
    }

    @Test
    @Sql(scripts = "/5-employees-over-65.sql")
    void countEmployeesByBirthDateLessThan65YearsAgoShouldReturn5() {
        Long count = employeeRepository.countEmployeesByBirthDateLessThan(LocalDate.now().minusYears(65));
        assertEquals(5, count);
    }

    @Test
    @Sql(scripts = "/5-employees-under-18.sql")
    void countEmployeesBySectorAndBirthDateGreaterThan18YearsAgoShouldReturn5() {
        Long count = employeeRepository.countEmployeesBySectorAndBirthDateGreaterThan(Sector.builder().id(1L).build(), LocalDate.now().minusYears(18));
        assertEquals(5, count);
    }

    @Test
    @Sql(scripts = "/5-employees-by-each-sector.sql")
    void countEmployeesBySectorShouldReturn5() {
        Long countSector1 = employeeRepository.countEmployeesBySector(Sector.builder().id(1L).build());
        Long countSector2 = employeeRepository.countEmployeesBySector(Sector.builder().id(2L).build());
        assertEquals(5, countSector1);
        assertEquals(5, countSector2);
    }

    @Test
    @Sql("/create-employee-to-be-deleted.sql")
    void deleteEmployeeByIdShouldWorkFine() {
        String cpf = "12345678901";
        assertTrue(employeeRepository.findById(cpf).isPresent());
        employeeRepository.deleteById(cpf);
        assertFalse(employeeRepository.findById(cpf).isPresent());
    }

}
