package com.orakuma.servitus.organ;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "organ")
public class OrganController {
  private final OrganService organService;

  public OrganController(OrganService organService) {
    this.organService = organService;
  }

  @GetMapping
  @ApiResponse(responseCode = "200", description = "Fetches and gets all categories")
  public ResponseEntity<Iterable<OrganDto>> getAll() {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(organService.gets());
  }

  @PostMapping
  @ApiResponse(responseCode = "201", description = "")
  @PreAuthorize(value = "hasAnyRole('ADMIN', 'ORGAN_ADMIN')")
  public ResponseEntity<Optional<OrganDto>> create(@RequestBody OrganDto organDto) {
    return new ResponseEntity<>(organService.persist(organDto), HttpStatus.CREATED);
  }

  @GetMapping("/internal/{id}")
  @ApiResponse(responseCode = "200 or 404", description = "Gets organ by Id")
  public ResponseEntity<Optional<OrganDto>> get(@PathVariable("id") Long id) {
    Optional<OrganDto> organDto = organService.findBy(id);
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(organDto);
  }

  @GetMapping("/{publicId}")
  @ApiResponse(responseCode = "200 or 404", description = "Gets organ by publicId")
  public ResponseEntity<OrganDto> getByPublicId(@PathVariable("publicId") String publicId) {
    OrganDto organDto = organService.getOrganByPublicId(publicId);
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(organDto);
  }

  @PatchMapping("/inactivate/{id}")
  @ApiResponse(responseCode = "200", description = "Inactivate the selected organisation")
  public ResponseEntity<Integer> inactivate(@PathVariable("id") Long id) {
    int inactivatedOrgan = organService.inactivate(id);
    HttpStatus responseStatus = HttpStatus.OK;
    if (inactivatedOrgan == -1) {
      responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    } else if (inactivatedOrgan == 0) {
      responseStatus = HttpStatus.FORBIDDEN;
    }
    return new ResponseEntity<>(inactivatedOrgan, responseStatus);
  }

  @PatchMapping("/set-publicId-manually/{id}")
  public void updateOrganWithPublicId(@PathVariable("id") Long id) {
    organService.setOrganPublicId(id);
  }
}
