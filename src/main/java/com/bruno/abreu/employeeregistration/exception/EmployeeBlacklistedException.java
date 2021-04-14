package com.bruno.abreu.employeeregistration.exception;

public class EmployeeBlacklistedException extends RuntimeException {

    public EmployeeBlacklistedException() {
        super("Employee is blacklisted!");
    }
}
