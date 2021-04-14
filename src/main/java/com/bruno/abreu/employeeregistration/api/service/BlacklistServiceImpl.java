package com.bruno.abreu.employeeregistration.api.service;

import com.bruno.abreu.employeeregistration.api.BlacklistApi;
import com.bruno.abreu.employeeregistration.api.dto.PersonBlackListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BlacklistServiceImpl implements BlacklistService {

    private final
    BlacklistApi blacklistApi;

    @Autowired
    public BlacklistServiceImpl(BlacklistApi blacklistApi) {
        this.blacklistApi = blacklistApi;
    }

    @Override
    public Boolean exist(String cpf) throws IOException, InterruptedException {
        List<PersonBlackListDto> blacklistDto = blacklistApi.findByCpf(cpf);
        return !blacklistDto.isEmpty();
    }
}
