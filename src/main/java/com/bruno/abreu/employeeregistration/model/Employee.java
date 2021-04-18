package com.bruno.abreu.employeeregistration.model;

import com.bruno.abreu.employeeregistration.model.serializer.EmployeeSectorSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    @NotBlank(message = "cpf must not be empty")
    private String cpf;

    @NotBlank(message = "name must not be empty")
    private String name;

    @NotBlank(message = "phone must not be empty")
    private String phone;

    @NotBlank(message = "email must not be empty")
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "birthDate must not be empty")
    private LocalDate birthDate;

    @JsonSerialize(using = EmployeeSectorSerializer.class)
    @ManyToOne
    @NotNull(message = "sector must not be empty")
    private Sector sector;
}
