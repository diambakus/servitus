package com.orakuma.servitus.unit;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("unit")
public class UnitController {
    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "List all units or by organisation")
    public ResponseEntity<?> getUnitsByOrganisation(@RequestParam(value = "organId", required = false) Long organId) {
        return ResponseEntity.ok(organId == null ? unitService.gets(): unitService.getByOrgan(organId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Registers new Unit")
    public ResponseEntity<?> post(@RequestBody UnitDto unitDto) {
        return new ResponseEntity(unitService.create(unitDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @ApiResponse(responseCode = "200", description = "Get unit by identity")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return new ResponseEntity(unitService.getBy(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{unitId}/organ")
    @ApiResponse(responseCode = "200", description = "Given unit Id, get the corresponding organ")
    public ResponseEntity<?> getOrgan(@PathVariable("unitId") Long unitId) {
        return new ResponseEntity(unitService.getOrgan(unitId), HttpStatus.OK);
    }

    @PatchMapping(value = "/{unitId}")
    @ApiResponse(responseCode = "200", description = "Given unit Id, inactivate the unit")
    public ResponseEntity<?> inactive(@PathVariable("unitId") Long unitId) {
        int inactivatedUnit = unitService.inactivate(unitId);
        HttpStatus responseStatus = HttpStatus.OK;
        if (inactivatedUnit == -1) {
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (inactivatedUnit == 0) {
            responseStatus = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(inactivatedUnit, responseStatus);
    }
}
