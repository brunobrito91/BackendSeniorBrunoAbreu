package com.bruno.abreu.employeeregistration.model.serializer;

import com.bruno.abreu.employeeregistration.model.Employee;
import com.bruno.abreu.employeeregistration.model.serializer.dto.EmployeeDto;
import com.bruno.abreu.employeeregistration.util.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SectorEmployeeSerializer extends JsonSerializer<List<Employee>> {

    @Override
    public void serialize(List<Employee> employees, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<EmployeeDto> employeeList = employees.stream()
                .map(employee -> EmployeeDto.builder()
                        .name(employee.getName())
                        .email(employee.getEmail())
                        .age(DateUtil.getAge(employee.getBirthDate()))
                        .build())
                .collect(Collectors.toList());
        jsonGenerator.writeObject(employeeList);
    }
}
