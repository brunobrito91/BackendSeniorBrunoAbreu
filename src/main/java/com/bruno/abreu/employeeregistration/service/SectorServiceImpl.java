package com.bruno.abreu.employeeregistration.service;

import com.bruno.abreu.employeeregistration.model.Sector;
import com.bruno.abreu.employeeregistration.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class SectorServiceImpl implements Service<Sector> {

    private final SectorRepository sectorRepository;

    @Autowired
    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @Override
    public List<Sector> findAll() {
        return sectorRepository.findAll();
    }
}
