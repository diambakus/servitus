package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.OrganDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("units")
public class UnitController {
  private final UnitService unitService;

  public UnitController(UnitService unitService) {
    this.unitService = unitService;
  }

  @GetMapping
  @ApiResponse(responseCode = "200", description = "List all units or by organisation")
  public ResponseEntity<List<UnitDto>> getUnitsByOrganisation(
      @RequestParam(value = "publicId", required = false) String publicId) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            StringUtils.isBlank(publicId) ? unitService.gets() : unitService.getByOrgan(publicId));
  }

  @PostMapping
  @ApiResponse(responseCode = "201", description = "Registers new Unit")
  @PreAuthorize(value = "hasAnyRole('ADMIN', 'ORGAN_ADMIN')")
  public ResponseEntity<Optional<UnitDto>> post(@RequestBody UnitDto unitDto) {
    return new ResponseEntity<>(unitService.create(unitDto), HttpStatus.CREATED);
  }

  @GetMapping(value = "/internal/{id}")
  @ApiResponse(responseCode = "200", description = "Get unit by identity")
  public ResponseEntity<Optional<UnitDto>> get(@PathVariable("id") Long id) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(unitService.getBy(id));
  }

  @GetMapping(value = "/{publicId}")
  @ApiResponse(responseCode = "200", description = "Get unit by publicId")
  public ResponseEntity<UnitDto> getByPublicId(@PathVariable("publicId") String publicId) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(unitService.getByPublicId(publicId));
  }

  @GetMapping(value = "/{unitId}/organ")
  @ApiResponse(responseCode = "200", description = "Given unit Id, get the corresponding organ")
  public ResponseEntity<Optional<OrganDto>> getOrgan(@PathVariable("unitId") Long unitId) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(Optional.of(unitService.getOrgan(unitId)));
  }

  @PatchMapping(value = "/{unitId}")
  @ApiResponse(responseCode = "200", description = "Given unit Id, inactivate the unit")
  @PreAuthorize(value = "hasAnyRole('ADMIN', 'ORGAN_ADMIN')")
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
