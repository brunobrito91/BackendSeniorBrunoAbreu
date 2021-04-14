package com.bruno.abreu.employeeregistration.service.business;

import com.bruno.abreu.employeeregistration.api.service.BlacklistService;
import com.bruno.abreu.employeeregistration.model.Employee;
import com.bruno.abreu.employeeregistration.repository.EmployeeRepository;
import com.bruno.abreu.employeeregistration.util.DateUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeBusinessServiceImpl implements EmployeeBusinessService {

    private final
    EmployeeRepository employeeRepository;

    private final
    BlacklistService blacklistService;

    public EmployeeBusinessServiceImpl(EmployeeRepository employeeRepository, BlacklistService blacklistService) {
        this.employeeRepository = employeeRepository;
        this.blacklistService = blacklistService;
    }

    @Override
    public boolean hasSectorLimitForEmployeesOver65BeenExceeded() {
        long numberOfEmployees = employeeRepository.count();
        if (numberOfEmployees == 0) {
            return true;
        }

        Long numberOfEmployeesOver = employeeRepository.countEmployeesByBirthDateLessThan(DateUtil.getDateFromYearsAgo(65));

        long percentageOfEmployeesOver65 = (numberOfEmployeesOver + 1) * 100 / numberOfEmployees;

        return percentageOfEmployeesOver65 > 20;
    }

    @Override
    public boolean hasSectorLimitForEmployeesUnder18BeenExceeded(Employee employee) {
        Long numberOfEmployeesBySector = employeeRepository.countEmployeesBySector(employee.getSector());
        if (numberOfEmployeesBySector == 0) {
            return true;
        }

        Long numberOfEmployeesUnder18BySector = employeeRepository.countEmployeesBySectorAndBirthDateGreaterThan(
                employee.getSector(), DateUtil.getDateFromYearsAgo(18)
        );

        long percentageOfEmployeesUnder18BySector = (numberOfEmployeesUnder18BySector + 1) * 100 / numberOfEmployeesBySector;

        return percentageOfEmployeesUnder18BySector > 20;
    }

    @Override
    public boolean isBlacklisted(Employee employee) throws IOException, InterruptedException {
        return blacklistService.exist(employee.getCpf());
    }
}
