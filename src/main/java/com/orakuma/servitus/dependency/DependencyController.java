package com.orakuma.servitus.dependency;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.Optional;

@RestController
@RequestMapping(path = "dependency")
@AllArgsConstructor
public class DependencyController {
    private final DependencyService dependencyService;

    @PostMapping
    public ResponseEntity<Optional<DependencyDto>> create(@RequestBody DependencyDto dependencyDto) {
        return new ResponseEntity<>(dependencyService.persist(dependencyDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<LinkedHashSet<DependencyDto>> getAll() {
        return new ResponseEntity<>(dependencyService.fetchAll(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<DependencyDto>> deactivate(@PathVariable("id") Long id) {
        return new ResponseEntity<>(dependencyService.deactivate(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Optional<DependencyDto>> update(@RequestBody DependencyDto dependencyDto) {
        return new ResponseEntity<>(dependencyService.update(dependencyDto), HttpStatus.OK);
    }
}
