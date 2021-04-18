package com.bruno.abreu.employeeregistration.service;

import java.util.List;

public interface Service<T> {

    default T insert(T entity) {
        throw new UnsupportedOperationException();
    }

    default void remove(String id) {
        throw new UnsupportedOperationException();
    }

    default T findById(String id) {
        throw new UnsupportedOperationException();
    }

    List<T> findAll();
}
