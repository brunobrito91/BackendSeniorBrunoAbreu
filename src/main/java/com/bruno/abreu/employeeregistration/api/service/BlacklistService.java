package com.bruno.abreu.employeeregistration.api.service;

import java.io.IOException;

public interface BlacklistService {

    Boolean exist(String cpf) throws IOException, InterruptedException;
}
