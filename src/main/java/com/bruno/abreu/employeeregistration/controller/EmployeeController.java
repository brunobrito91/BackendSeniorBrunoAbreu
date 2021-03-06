package com.bruno.abreu.employeeregistration.controller;

import com.bruno.abreu.employeeregistration.model.Employee;
import com.bruno.abreu.employeeregistration.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final Service<Employee> employeeService;

    public EmployeeController(Service<Employee> employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> insert(@Valid @RequestBody Employee employee) {
        Employee newEmployee = employeeService.insert(employee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String cpf) {
        employeeService.remove(cpf);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable("id") String cpf) {
        Employee employee = employeeService.findById(cpf);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employee);
    }
}
