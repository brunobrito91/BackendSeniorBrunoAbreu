package com.bruno.abreu.employeeregistration.controller;

import com.bruno.abreu.employeeregistration.model.Sector;
import com.bruno.abreu.employeeregistration.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sectors")
public class SectorController {

    private final Service<Sector> sectorService;

    public SectorController(Service<Sector> sectorService) {
        this.sectorService = sectorService;
    }

    @GetMapping
    public ResponseEntity<List<Sector>> findAll() {
        List<Sector> sectors = sectorService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sectors);
    }
}
