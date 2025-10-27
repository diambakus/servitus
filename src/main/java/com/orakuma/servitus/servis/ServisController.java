package com.orakuma.servitus.servis;

import com.orakuma.servitus.dependency.DependencyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("servis")
public class ServisController {
    private final ServisService servisService;

    public ServisController(ServisService servisService) {
        this.servisService = servisService;
    }

    @GetMapping
    public ResponseEntity<List<ServisDto>> getAllServis(){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(servisService.getAll());
    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<List<ServisDto>> getAllServisByUnitId(@PathVariable("unitId") Long unitId) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(servisService.getByUnit(unitId));
    }

    @PostMapping
    public ResponseEntity<ServisDto> addServis(@RequestBody ServisDto servisDto) {
        return new ResponseEntity<>(servisService.create(servisDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ServisDto> getServisById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(servisService.get(id));
    }

    @GetMapping("/{id}/dependencies")
    public ResponseEntity<Set<DependencyDto>> getDependencies(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(servisService.getDependenciesForServis(id));
    }

    @PostMapping("/{id}/dependencies")
    public ResponseEntity<Void> addDependencies(@PathVariable("id") Long id, @RequestBody LinkedHashSet<Long> dependenciesId) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(servisService.addDependencies(id, dependenciesId));
    }
}
