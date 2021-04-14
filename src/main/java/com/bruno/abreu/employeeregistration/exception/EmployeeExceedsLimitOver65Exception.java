package com.bruno.abreu.employeeregistration.exception;

public class EmployeeExceedsLimitOver65Exception extends RuntimeException {

    public EmployeeExceedsLimitOver65Exception() {
        super("Employee exceeds the limit of employees over 65!");
    }
}
