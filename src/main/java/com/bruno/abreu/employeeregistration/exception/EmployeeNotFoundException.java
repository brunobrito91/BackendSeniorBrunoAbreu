package com.bruno.abreu.employeeregistration.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException() {
        super("Employee not found!");
    }
}
