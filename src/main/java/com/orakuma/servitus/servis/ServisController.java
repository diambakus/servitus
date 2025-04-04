package com.orakuma.servitus.servis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "servis/")
public class ServisController {
    private ServisService servisService;
    private static final Logger LOG = LoggerFactory.getLogger(ServisController.class);


    public ServisController(ServisService servisService) {
        this.servisService = servisService;
    }

    @GetMapping("{unitId}")
    public ResponseEntity<?> getAllServisByUnitId(@PathVariable("unitId") Long unitId) {
        return new ResponseEntity<>(servisService.getByUnit(unitId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addServis(@RequestBody ServisDto servisDto) {
        return new ResponseEntity<>(servisService.create(servisDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getServisById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(servisService.get(id), HttpStatus.OK);
    }

    @PatchMapping(value = "{id}/add-requisites")
    public ResponseEntity<?> addToUnits(@PathVariable("id") Long id, @RequestBody Map<Integer, String> requisites) {
        return new ResponseEntity<>(servisService.addRequisites(id, requisites), HttpStatus.OK);
    }

    @PatchMapping(value = "{id}/remove-requisites")
    public ResponseEntity<?> removeFromUnits(@PathVariable("id") Long id, @RequestBody Map<Integer, String> requisites) {
        servisService.removeRequisites(id, requisites.keySet());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
