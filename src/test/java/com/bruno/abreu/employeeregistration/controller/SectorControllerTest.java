package com.bruno.abreu.employeeregistration.controller;

import com.bruno.abreu.employeeregistration.model.Employee;
import com.bruno.abreu.employeeregistration.model.Sector;
import com.bruno.abreu.employeeregistration.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SectorController.class)
class SectorControllerTest {

    private static List<Sector> sectors;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private Service<Sector> sectorService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        sectors = List.of(Sector.builder()
                        .id(1L)
                        .description("Sector 1")
                        .employees(List.of(Employee.builder()
                                        .cpf("12345678901")
                                        .name("Employee 1")
                                        .email("employee1@employee.com")
                                        .phone("999999991")
                                        .birthDate(LocalDate.now().minusYears(1))
                                        .build(),
                                Employee.builder()
                                        .cpf("12345678902")
                                        .name("Employee 2")
                                        .email("employee2@employee.com")
                                        .phone("999999992")
                                        .birthDate(LocalDate.now().minusYears(2))
                                        .build(),
                                Employee.builder()
                                        .cpf("12345678903")
                                        .name("Employee 3")
                                        .email("employee3@employee.com")
                                        .phone("999999993")
                                        .birthDate(LocalDate.now().minusYears(3))
                                        .build()))
                        .build(),
                Sector.builder()
                        .id(2L)
                        .description("Sector 2")
                        .employees(List.of(Employee.builder()
                                        .cpf("12345678904")
                                        .name("Employee 4")
                                        .email("employee4@employee.com")
                                        .phone("999999994")
                                        .birthDate(LocalDate.now().minusYears(4))
                                        .build(),
                                Employee.builder()
                                        .cpf("12345678905")
                                        .name("Employee 5")
                                        .email("employee5@employee.com")
                                        .phone("999999995")
                                        .birthDate(LocalDate.now().minusYears(5))
                                        .build(),
                                Employee.builder()
                                        .cpf("12345678906")
                                        .name("Employee 6")
                                        .email("employee6@employee.com")
                                        .phone("999999996")
                                        .birthDate(LocalDate.now().minusYears(6))
                                        .build()))
                        .build(),
                Sector.builder()
                        .id(3L)
                        .description("Sector 3")
                        .employees(List.of(Employee.builder()
                                        .cpf("12345678907")
                                        .name("Employee 7")
                                        .email("employee7@employee.com")
                                        .phone("999999997")
                                        .birthDate(LocalDate.now().minusYears(7))
                                        .build(),
                                Employee.builder()
                                        .cpf("12345678908")
                                        .name("Employee 8")
                                        .email("employee8@employee.com")
                                        .phone("999999998")
                                        .birthDate(LocalDate.now().minusYears(8))
                                        .build(),
                                Employee.builder()
                                        .cpf("12345678909")
                                        .name("Employee 9")
                                        .email("employee9@employee.com")
                                        .phone("999999999")
                                        .birthDate(LocalDate.now().minusYears(9))
                                        .build()))
                        .build());
    }

    @Test
    void findAllEmployeeGroupedBySectorShouldReturnStatusOk() throws Exception {
        when(sectorService.findAll()).thenReturn(sectors);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/sectors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findAllEmployeeGroupedBySectorShouldReturnSectorsOnResponseBody() throws Exception {
        String content = objectMapper.writeValueAsString(sectors);
        when(sectorService.findAll()).thenReturn(sectors);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/sectors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(content));
    }
}
