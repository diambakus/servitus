package com.orakuma.servitus.servis;

import com.orakuma.servitus.dependency.DependencyDto;
import com.orakuma.servitus.dependency.DependencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("servis")
public class ServisController {
  private final ServisService servisService;
  private final DependencyService dependencyService;

  public ServisController(ServisService servisService, DependencyService dependencyService) {
    this.servisService = servisService;
    this.dependencyService = dependencyService;
  }

  @GetMapping
  public ResponseEntity<List<ServisDto>> getAllService() {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(servisService.getAll());
  }

  @GetMapping("/unit/{unitPublicId}")
  public ResponseEntity<List<ServisDto>> getAllServiceByUnitPublicId(
      @PathVariable("unitPublicId") String unitPublicId) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(servisService.getByUnitPublicId(unitPublicId));
  }

  @GetMapping("/internal/unit/{unitId}/service-ids")
  public ResponseEntity<List<Long>> getAllServiceByUnitId(@PathVariable("unitId") Long unitId) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(servisService.getServicesIdsByUnitId(unitId));
  }

  @PostMapping
  @PreAuthorize(value = "hasAnyRole('ADMIN', 'ORGAN_ADMIN')")
  public ResponseEntity<ServisDto> addService(@RequestBody ServisDto servisDto) {
    return new ResponseEntity<>(servisService.create(servisDto), HttpStatus.CREATED);
  }

  @GetMapping(value = "/internal/{id}")
  public ResponseEntity<ServisDto> getServiceById(@PathVariable("id") Long id) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(servisService.get(id));
  }

  @GetMapping("/{publicId}")
  public ResponseEntity<ServisDto> getServiceByPublicId(@PathVariable("publicId") String publicId) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(servisService.getServisByPublicId(publicId));
  }

  @DeleteMapping("/{publicId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'ORGAN_ADMIN')")
  public ResponseEntity<Void> deleteServis(@PathVariable("publicId") String publicId) {
    servisService.deleteByPublicId(publicId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{publicId}/dependencies")
  public ResponseEntity<Set<DependencyDto>> getDependencies(
      @PathVariable("publicId") String publicId) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dependencyService.getDependenciesByServisPublicId(publicId));
  }

  /** Dependencies not yet attached to the servis, i.e. the ones that can still be added. */
  @GetMapping("/{publicId}/dependencies/available")
  public ResponseEntity<Set<DependencyDto>> getAvailableDependencies(
      @PathVariable("publicId") String publicId) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dependencyService.getDependenciesToAddByServisPublicId(publicId));
  }

  @PostMapping("/{publicId}/dependencies/add")
  @PreAuthorize(value = "hasAnyRole('ADMIN', 'ORGAN_ADMIN')")
  public ResponseEntity<Void> addDependencies(
      @PathVariable("publicId") String publicId,
      @RequestBody LinkedHashSet<Long> dependenciesId) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(servisService.addDependencies(publicId, dependenciesId));
  }

  @PostMapping("/{publicId}/dependencies/remove")
  @PreAuthorize(value = "hasAnyRole('ADMIN', 'ORGAN_ADMIN')")
  public ResponseEntity<Void> removeDependencies(
      @PathVariable("publicId") String publicId,
      @RequestBody LinkedHashSet<Long> dependenciesId) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(servisService.removeDependencies(publicId, dependenciesId));
  }
}
