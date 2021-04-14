package com.bruno.abreu.employeeregistration.service;

public interface Service<T> {

    T insert(T employee);

    void remove(String id);
}
