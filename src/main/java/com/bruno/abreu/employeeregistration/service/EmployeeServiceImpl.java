package com.bruno.abreu.employeeregistration.service;

import com.bruno.abreu.employeeregistration.exception.*;
import com.bruno.abreu.employeeregistration.model.Employee;
import com.bruno.abreu.employeeregistration.repository.EmployeeRepository;
import com.bruno.abreu.employeeregistration.service.business.EmployeeBusinessService;
import com.bruno.abreu.employeeregistration.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@org.springframework.stereotype.Service
public class EmployeeServiceImpl implements Service<Employee> {

    private final
    EmployeeRepository employeeRepository;

    private final
    EmployeeBusinessService employeeBusinessService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeBusinessService employeeBusinessService) {
        this.employeeRepository = employeeRepository;
        this.employeeBusinessService = employeeBusinessService;
    }

    @Override
    public Employee insert(Employee employee) {
        if (employeeRepository.existsById(employee.getCpf())) {
            throw new EmployeeAlreadyRegisteredException();
        }
        try {
            if (employeeBusinessService.isBlacklisted(employee)) {
                throw new EmployeeBlacklistedException();
            }
        } catch (IOException | InterruptedException e) {
            throw new BlacklistApiException(e.getMessage());
        }

        Integer employeeAge = DateUtil.getAge(employee.getBirthDate());

        if (employeeAge < 18 && employeeBusinessService.hasSectorLimitForEmployeesUnder18BeenExceeded(employee)) {
            throw new EmployeeExceedsLimitUnder18Exception();
        }

        if (employeeAge > 65 && employeeBusinessService.hasSectorLimitForEmployeesOver65BeenExceeded()) {
            throw new EmployeeExceedsLimitOver65Exception();
        }

        return employeeRepository.save(employee);
    }
}
