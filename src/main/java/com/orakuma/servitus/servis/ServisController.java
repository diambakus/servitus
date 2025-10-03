package com.orakuma.servitus.servis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("servis")
public class ServisController {
    private final ServisService servisService;
    private static final Logger LOG = LoggerFactory.getLogger(ServisController.class);

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

    @PatchMapping(value = "{id}/add-requisites")
    public ResponseEntity<ServisDto> addToUnits(@PathVariable("id") Long id, @RequestBody Map<Integer, String> requisites) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(servisService.addRequisites(id, requisites));
    }

    @PatchMapping(value = "{id}/remove-requisites")
    public ResponseEntity<?> removeFromUnits(@PathVariable("id") Long id, @RequestBody Set<Integer> requisitesPositions) {
        LOG.info("Removing requisites (with listed positions) from the servis: {}", requisitesPositions.toString());
        servisService.removeRequisites(id, requisitesPositions);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
