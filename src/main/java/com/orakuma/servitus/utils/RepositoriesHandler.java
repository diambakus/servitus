package com.orakuma.servitus.utils;

import com.orakuma.servitus.address.Address;
import com.orakuma.servitus.address.AddressRepository;
import com.orakuma.servitus.address.exceptions.AddressNotFoundException;
import com.orakuma.servitus.contact.Contact;
import com.orakuma.servitus.contact.ContactRepository;
import com.orakuma.servitus.contact.exceptions.ContactNotFoundException;
import com.orakuma.servitus.dependency.DependencyEntity;
import com.orakuma.servitus.dependency.DependencyRepository;
import com.orakuma.servitus.dependency.exceptions.DependencyNotFoundException;
import com.orakuma.servitus.organ.Organ;
import com.orakuma.servitus.organ.OrganRepository;
import com.orakuma.servitus.organ.exceptions.OrganNotFoundException;
import com.orakuma.servitus.servis.Servis;
import com.orakuma.servitus.servis.ServisRepository;
import com.orakuma.servitus.servis.exceptions.ServisNotFoundException;
import com.orakuma.servitus.unit.Unit;
import com.orakuma.servitus.unit.UnitRepository;
import com.orakuma.servitus.unit.exceptions.UnitNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RepositoriesHandler {
    private final UnitRepository    unitRepository;
    private final ServisRepository  servisRepository;
    private final OrganRepository   organRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final DependencyRepository dependencyRepository;

    public Organ getOrganById(Long id) {
        return organRepository.findById(id).orElseThrow(() -> {
            String errorMessage = String.format("Organ with id %s not found", id);
            return new OrganNotFoundException(errorMessage);
        });
    }

    public Servis getServisById(Long id) {
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

    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> {
            String errorMessage = String.format("Address with id %s not found", id);
            return new AddressNotFoundException(errorMessage);
        });
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id).orElseThrow(() -> {
            String errorMessage = String.format("Contact with id %s not found", id);
            return new ContactNotFoundException(errorMessage);
        });
    }

    public DependencyEntity getDependencyById(Long id) {
        return dependencyRepository.findById(id).orElseThrow(() -> {
            String errorMessage = String.format("Dependency with id %s not found", id);
            return new DependencyNotFoundException(errorMessage);
        });
    }
}
