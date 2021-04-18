package com.bruno.abreu.employeeregistration.model.serializer;

import com.bruno.abreu.employeeregistration.model.Sector;
import com.bruno.abreu.employeeregistration.model.serializer.dto.SectorDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EmployeeSectorSerializer extends JsonSerializer<Sector> {

    @Override
    public void serialize(Sector sector, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        SectorDto sectorDto = SectorDto.builder()
                .id(sector.getId())
                .description(sector.getDescription())
                .build();
        jsonGenerator.writeObject(sectorDto);
    }
}
