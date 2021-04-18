package com.bruno.abreu.employeeregistration.model.serializer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectorDto {

    private Long id;
    private String description;
}
