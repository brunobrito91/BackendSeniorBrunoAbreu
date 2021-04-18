package com.bruno.abreu.employeeregistration.repository;

import com.bruno.abreu.employeeregistration.model.Employee;
import com.bruno.abreu.employeeregistration.model.Sector;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    Long countEmployeesByBirthDateLessThan(
            @NotNull(message = "birthDate must not be empty") LocalDate birthDate);

    Long countEmployeesBySectorAndBirthDateGreaterThan(
            @NotNull(message = "sector must not be empty") Sector sector,
            @NotNull(message = "birthDate must not be empty") LocalDate birthDate);

    Long countEmployeesBySector(
            @NotNull(message = "sector must not be empty") Sector sector);

    List<Employee> findAll();
}
