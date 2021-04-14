package com.bruno.abreu.employeeregistration.exception;

public class EmployeeExceedsLimitUnder18Exception extends RuntimeException {

    public EmployeeExceedsLimitUnder18Exception() {
        super("Employee exceeds the limit of employees under 18!");
    }
}
