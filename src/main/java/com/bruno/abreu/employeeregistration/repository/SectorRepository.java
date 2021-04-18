package com.bruno.abreu.employeeregistration.repository;

import com.bruno.abreu.employeeregistration.model.Sector;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SectorRepository extends CrudRepository<Sector, Long> {

    List<Sector> findAll();
}
