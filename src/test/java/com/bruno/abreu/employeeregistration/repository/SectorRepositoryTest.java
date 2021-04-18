package com.bruno.abreu.employeeregistration.repository;

import com.bruno.abreu.employeeregistration.model.Sector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class SectorRepositoryTest {

    @Autowired
    SectorRepository sectorRepository;

    @Test
    @Sql(scripts = "/5-employees.sql")
    void findAllEmployeesGroupedBySectorShouldReturn5Employes() {
        List<Sector> sectors = sectorRepository.findAll();
        Integer numberOfEmployees =
                sectors.stream()
                        .reduce(0, (partialResult, sector) -> partialResult + sector.getEmployees().size(), Integer::sum);
        assertEquals(5, numberOfEmployees);
    }

    @Test
    void findAllEmployeesGroupedBySectorShouldReturn3Sectors() {
        List<Sector> sectors = sectorRepository.findAll();
        assertEquals(3, sectors.size());
    }
}
