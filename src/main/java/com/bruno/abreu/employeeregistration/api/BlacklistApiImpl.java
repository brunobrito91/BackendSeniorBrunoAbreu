package com.bruno.abreu.employeeregistration.api;

import com.bruno.abreu.employeeregistration.api.dto.PersonBlackListDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class BlacklistApiImpl implements BlacklistApi {

    private final ObjectMapper objectMapper;

    @Autowired
    public BlacklistApiImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<PersonBlackListDto> findByCpf(String cpf) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String uri = "https://5e74cb4b9dff120016353b04.mockapi.io/api/v1/blacklist?search=CPF";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri.replace("CPF", cpf)))
                .GET()
                .build();

        String responseBody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return objectMapper.readValue(responseBody, new TypeReference<>() {
        });
    }
}
