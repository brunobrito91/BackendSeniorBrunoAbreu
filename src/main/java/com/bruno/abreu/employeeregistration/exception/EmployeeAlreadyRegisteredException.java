package com.bruno.abreu.employeeregistration.exception;

public class EmployeeAlreadyRegisteredException extends RuntimeException {

    public EmployeeAlreadyRegisteredException() {
        super("Employee is already registered!");
    }
}
