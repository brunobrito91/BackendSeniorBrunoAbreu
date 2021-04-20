package com.bruno.abreu.employeeregistration.model;

import com.bruno.abreu.employeeregistration.model.serializer.SectorEmployeeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sector {

    @Id
    private Long id;

    private String description;

    @JsonSerialize(using = SectorEmployeeSerializer.class)
    @OneToMany(mappedBy = "sector")
    private List<Employee> employees;
}
