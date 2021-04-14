package com.bruno.abreu.employeeregistration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonBlackListDto {
    private Long id;
    private LocalDateTime createdAt;
    private String name;
    private String cpf;
}
