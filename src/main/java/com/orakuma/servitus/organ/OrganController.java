package com.orakuma.servitus.organ;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(organService.gets());
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "")
    public ResponseEntity<Optional<OrganDto>> create(@RequestBody OrganDto organDto) {
        return new ResponseEntity<>(organService.persist(organDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200 or 404", description = "Gets organ by Id")
    public ResponseEntity<Optional<OrganDto>> get(@PathVariable("id") Long id) {
        Optional<OrganDto> organDto = organService.findBy(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(organDto);
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
}
