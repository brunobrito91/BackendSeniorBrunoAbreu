package com.bruno.abreu.employeeregistration.service.business;

import com.bruno.abreu.employeeregistration.api.service.BlacklistServiceImpl;
import com.bruno.abreu.employeeregistration.model.Employee;
import com.bruno.abreu.employeeregistration.model.Sector;
import com.bruno.abreu.employeeregistration.repository.EmployeeRepository;
import com.bruno.abreu.employeeregistration.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeBusinessServiceImplTest {

    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    BlacklistServiceImpl blacklistService;

    @Autowired
    EmployeeBusinessServiceImpl employeeBusinessService;

    @Test
    void employeeUnder18AndSectorLimitExceededShouldReturnTrue() {
        Employee employeeUnder18 = Employee.builder()
                .birthDate(LocalDate.now().minusYears(17))
                .sector(Sector.builder()
                        .id(1L)
                        .build())
                .build();

        when(employeeRepository.countEmployeesBySector(employeeUnder18.getSector()))
                .thenReturn(100L);
        when(employeeRepository.countEmployeesBySectorAndBirthDateGreaterThan(employeeUnder18.getSector(), DateUtil.getDateFromYearsAgo(18)))
                .thenReturn(20L);

        assertTrue(employeeBusinessService.hasSectorLimitForEmployeesUnder18BeenExceeded(employeeUnder18));
    }

    @Test
    void employeeUnder18AndSectorLimitNotExceededShouldReturnFalse() {
        Employee employeeUnder18 = Employee.builder()
                .birthDate(LocalDate.now().minusYears(17))
                .sector(Sector.builder()
                        .id(1L)
                        .build())
                .build();

        when(employeeRepository.countEmployeesBySector(employeeUnder18.getSector()))
                .thenReturn(100L);
        when(employeeRepository.countEmployeesBySectorAndBirthDateGreaterThan(employeeUnder18.getSector(), DateUtil.getDateFromYearsAgo(18)))
                .thenReturn(19L);

        assertFalse(employeeBusinessService.hasSectorLimitForEmployeesUnder18BeenExceeded(employeeUnder18));
    }

    @Test
    void employeeOver65AndCompanyLimitExceededShouldReturnTrue() {
        Employee employeeOver65 = Employee.builder()
                .birthDate(LocalDate.now().minusYears(66))
                .sector(Sector.builder()
                        .id(1L)
                        .build())
                .build();

        when(employeeRepository.count())
                .thenReturn(100L);
        when(employeeRepository.countEmployeesByBirthDateLessThan(DateUtil.getDateFromYearsAgo(65)))
                .thenReturn(20L);

        assertTrue(employeeBusinessService.hasSectorLimitForEmployeesOver65BeenExceeded());
    }

    @Test
    void employeeOver65AndCompanyLimitNotExceededShouldReturnFalse() {
        Employee employeeOver65 = Employee.builder()
                .birthDate(LocalDate.now().minusYears(66))
                .sector(Sector.builder()
                        .id(1L)
                        .build())
                .build();

        when(employeeRepository.count())
                .thenReturn(100L);
        when(employeeRepository.countEmployeesByBirthDateLessThan(DateUtil.getDateFromYearsAgo(65)))
                .thenReturn(19L);

        assertFalse(employeeBusinessService.hasSectorLimitForEmployeesOver65BeenExceeded());
    }

    @Test
    void employeeBlacklistedShouldReturnTrue() throws IOException, InterruptedException {
        Employee employeeBlacklisted = Employee.builder().build();

        when(blacklistService.exist(employeeBlacklisted.getCpf())).thenReturn(true);

        assertTrue(employeeBusinessService.isBlacklisted(employeeBlacklisted));
    }

    @Test
    void employeeNotBlacklistedShouldReturnFalse() throws IOException, InterruptedException {
        Employee employeeNotBlacklisted = Employee.builder().build();

        when(blacklistService.exist(employeeNotBlacklisted.getCpf())).thenReturn(false);

        assertFalse(employeeBusinessService.isBlacklisted(employeeNotBlacklisted));
    }
}
