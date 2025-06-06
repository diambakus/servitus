package com.orakuma.servitus.utils;

import com.orakuma.servitus.organ.Organ;
import com.orakuma.servitus.organ.OrganRepository;
import com.orakuma.servitus.organ.exceptions.OrganNotFoundException;
import com.orakuma.servitus.servis.Servis;
import com.orakuma.servitus.servis.ServisRepository;
import com.orakuma.servitus.servis.exceptions.ServisNotFoundException;
import com.orakuma.servitus.unit.Unit;
import com.orakuma.servitus.unit.UnitRepository;
import com.orakuma.servitus.unit.exceptions.UnitNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RepositoriesHandler {
    private final UnitRepository unitRepository;
    private final ServisRepository servisRepository;
    private final OrganRepository organRepository;

    public RepositoriesHandler(UnitRepository unitRepository, ServisRepository servisRepository, OrganRepository organRepository) {
        this.unitRepository = unitRepository;
        this.servisRepository = servisRepository;
        this.organRepository = organRepository;
    }

    public Organ getOrganById(Long id){
        return organRepository.findById(id).orElseThrow(() -> {
            String errorMessage = String.format("Organ with id %s not found", id);
            return new OrganNotFoundException(errorMessage);
        });
    }

    public Servis getServisById(Long id){
        return servisRepository.findById(id).orElseThrow(() -> {
            String errorMessage = String.format("Servis with id %s not found", id);
            return new ServisNotFoundException(errorMessage);
        });
    }

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id).orElseThrow(() -> {
            String errorMessage = String.format("Unit with id %s not found", id);
            return new UnitNotFoundException(errorMessage);
        });
    }
}
