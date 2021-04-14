package com.bruno.abreu.employeeregistration.api;

import com.bruno.abreu.employeeregistration.api.dto.PersonBlackListDto;

import java.io.IOException;
import java.util.List;

public interface BlacklistApi {

    List<PersonBlackListDto> findByCpf(String cpf) throws IOException, InterruptedException;

}
