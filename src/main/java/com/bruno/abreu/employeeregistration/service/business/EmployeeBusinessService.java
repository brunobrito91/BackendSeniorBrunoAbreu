package com.bruno.abreu.employeeregistration.service.business;

import com.bruno.abreu.employeeregistration.model.Employee;

import java.io.IOException;

public interface EmployeeBusinessService {

    boolean hasSectorLimitForEmployeesOver65BeenExceeded();

    boolean hasSectorLimitForEmployeesUnder18BeenExceeded(Employee employee);

    boolean isBlacklisted(Employee employee) throws IOException, InterruptedException;
}
