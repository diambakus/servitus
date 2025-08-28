package com.orakuma.servitus.organ;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(organService.gets(), HttpStatus.OK);
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "")
    public ResponseEntity<?> create(@RequestBody OrganDto organDto) {
        return new ResponseEntity<>(organService.persist(organDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200 or 404", description = "Gets organ by Id")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<OrganDto> organDto = organService.findBy(id);
        if (organDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(organDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Updates organisation information")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Map<String, String> fieldsContentMap) {
        return new ResponseEntity<>(organService.updateOrganWithProperties(id, fieldsContentMap), HttpStatus.OK);
    }

    @PatchMapping("/inactivate/{id}")
    @ApiResponse(responseCode = "200", description = "Inactivate the selected organisation")
    public ResponseEntity<?> inactivate(@PathVariable("id") Long id) {
        int inactivatedOrgan = organService.inactivate(id);
        HttpStatus responseStatus = HttpStatus.OK;
        if (inactivatedOrgan == -1) {
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (inactivatedOrgan == 0) {
            responseStatus = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(inactivatedOrgan, responseStatus);
    }
}
