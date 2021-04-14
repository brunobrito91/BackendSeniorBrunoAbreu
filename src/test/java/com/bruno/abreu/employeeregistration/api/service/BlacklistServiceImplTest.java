package com.bruno.abreu.employeeregistration.api.service;

import com.bruno.abreu.employeeregistration.api.BlacklistApi;
import com.bruno.abreu.employeeregistration.api.dto.PersonBlackListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class BlacklistServiceImplTest {

    @Autowired
    BlacklistServiceImpl blacklistService;

    @MockBean
    BlacklistApi blacklistApi;

    @Test
    void existingCpfShouldReturnTrue() throws IOException, InterruptedException {
        when(blacklistApi.findByCpf("12345678900")).thenReturn(List.of(PersonBlackListDto.builder().build()));
        assertTrue(blacklistService.exist("12345678900"));
    }

    @Test
    void nonexistentCpfShouldReturnFalse() throws IOException, InterruptedException {
        when(blacklistApi.findByCpf("12345678900")).thenReturn(List.of());
        assertFalse(blacklistService.exist("12345678900"));
    }
}
