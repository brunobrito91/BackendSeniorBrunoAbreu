package com.bruno.abreu.employeeregistration.controller;

import com.bruno.abreu.employeeregistration.exception.*;
import com.bruno.abreu.employeeregistration.model.Employee;
import com.bruno.abreu.employeeregistration.model.Sector;
import com.bruno.abreu.employeeregistration.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.Month;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmployeeControllerTest {

    private static Employee employee;
    private static Employee incompleteEmployee;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private Service<Employee> employeeService;


    @BeforeAll
    static void setup() {
        employee = Employee.builder()
                .cpf("11122233344")
                .name("Employee 1")
                .phone("+5516999999999")
                .email("employee@employee.com")
                .birthDate(LocalDate.of(1952, Month.JANUARY, 1))
                .sector(Sector.builder()
                        .id(1L)
                        .description("Sector 1")
                        .build())
                .build();

        incompleteEmployee = Employee.builder().build();
    }

    @Test
    void insertEmployeeShouldReturnStatusCreated() throws Exception {
        String content = objectMapper.writeValueAsString(employee);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void insertEmployeeShouldReturnEmployeeOnResponseBody() throws Exception {
        String content = objectMapper.writeValueAsString(employee);
        when(employeeService.insert(employee)).thenReturn(employee);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(content));
    }

    @Test
    void insertIncompleteEmployeeShouldReturnStatusBadRequest() throws Exception {
        String content = objectMapper.writeValueAsString(incompleteEmployee);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void insertIncompleteEmployeeShouldReturnValidationErrors() throws Exception {
        String content = objectMapper.writeValueAsString(incompleteEmployee);
        String responseBody = "{\"status_code\":400,\"message\":\"[birthDate must not be empty, cpf must not be empty, email must not be empty, name must not be empty, phone must not be empty, sector must not be empty]\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    void insertEmployeeAlreadyRegisteredInAnotherSectorShouldReturnValidationErrors() throws Exception {
        String content = objectMapper.writeValueAsString(employee);
        String responseBody = "{\"status_code\":400,\"message\":\"Employee is already registered!\"}";

        when(employeeService.insert(employee)).thenThrow(new EmployeeAlreadyRegisteredException());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    void insertEmployeeUnder18ShouldReturnValidationErrors() throws Exception {
        String content = objectMapper.writeValueAsString(employee);
        String responseBody = "{\"status_code\":400,\"message\":\"Employee exceeds the limit of employees under 18!\"}";

        when(employeeService.insert(employee)).thenThrow(new EmployeeExceedsLimitUnder18Exception());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    void insertEmployeeOver65ShouldReturnValidationErrors() throws Exception {
        String content = objectMapper.writeValueAsString(employee);
        String responseBody = "{\"status_code\":400,\"message\":\"Employee exceeds the limit of employees over 65!\"}";

        when(employeeService.insert(employee)).thenThrow(new EmployeeExceedsLimitOver65Exception());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    void insertEmployeeThatExistOnBlacklistShouldReturnValidationErrors() throws Exception {
        String content = objectMapper.writeValueAsString(employee);
        String responseBody = "{\"status_code\":400,\"message\":\"Employee is blacklisted!\"}";

        when(employeeService.insert(employee)).thenThrow(new EmployeeBlacklistedException());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    void insertEmployeeInNonexistentSectorShouldReturnValidationErrors() throws Exception {
        String content = objectMapper.writeValueAsString(employee);
        String responseBody = "{\"status_code\":400,\"message\":\"Invalid sector!\"}";

        when(employeeService.insert(employee)).thenThrow(EntityNotFoundException.class);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    void removeEmployeeByIdShouldReturnStatusOk() throws Exception {
        String cpf = "12345678900";
        doNothing().when(employeeService).remove(cpf);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/employees/{id}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void removeEmployeeByIdNotSavedShouldReturnStatusNotFound() throws Exception {
        String cpf = "12345678900";
        doThrow(EmptyResultDataAccessException.class).when(employeeService).remove(cpf);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/employees/{id}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findEmployeeByIdShouldReturnStatusOk() throws Exception {
        String cpf = "12345678900";
        when(employeeService.findById(cpf)).thenReturn(employee);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/employees/{id}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findEmployeeByIdShouldReturnEmployeeOnResponseBody() throws Exception {
        String content = objectMapper.writeValueAsString(employee);
        String cpf = "12345678900";
        when(employeeService.findById(cpf)).thenReturn(employee);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/employees/{id}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(content));
    }

    @Test
    void findEmployeeNotSavedYetByIdShouldReturnStatusNotFound() throws Exception {
        String cpf = "12345678900";
        when(employeeService.findById(cpf)).thenThrow(EmployeeNotFoundException.class);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/employees/{id}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }



}
