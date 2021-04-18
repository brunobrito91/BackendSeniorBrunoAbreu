package com.bruno.abreu.employeeregistration.model.serializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class EmployeeDto {
    private String name;
    private String email;
    private Integer age;
}
