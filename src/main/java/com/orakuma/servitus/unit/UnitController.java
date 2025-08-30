package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.OrganDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("unit")
public class UnitController {
    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "List all units or by organisation")
    public ResponseEntity<List<UnitDto>> getUnitsByOrganisation(@RequestParam(value = "organId", required = false) Long organId) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(organId == null ? unitService.gets(): unitService.getByOrgan(organId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Registers new Unit")
    public ResponseEntity<Optional<UnitDto>> post(@RequestBody UnitDto unitDto) {
        return new ResponseEntity<>(unitService.create(unitDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    @ApiResponse(responseCode = "200", description = "Get unit by identity")
    public ResponseEntity<Optional<UnitDto>> get(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(unitService.getBy(id));
    }

    @GetMapping(value = "/{unitId}/organ")
    @ApiResponse(responseCode = "200", description = "Given unit Id, get the corresponding organ")
    public ResponseEntity<Optional<OrganDto>> getOrgan(@PathVariable("unitId") Long unitId) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Optional.of(unitService.getOrgan(unitId)));
    }

    @PatchMapping(value = "/{unitId}")
    @ApiResponse(responseCode = "200", description = "Given unit Id, inactivate the unit")
    public ResponseEntity<Integer> inactive(@PathVariable("unitId") Long unitId) {
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
