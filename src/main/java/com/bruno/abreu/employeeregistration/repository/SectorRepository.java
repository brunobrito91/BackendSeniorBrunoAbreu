package com.bruno.abreu.employeeregistration.repository;

import com.bruno.abreu.employeeregistration.model.Sector;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface SectorRepository extends CrudRepository<Sector, Long> {

    List<Sector> findAll();
}
