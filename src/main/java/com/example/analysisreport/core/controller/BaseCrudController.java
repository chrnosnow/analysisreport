package com.example.analysisreport.core.controller;

import com.example.analysisreport.core.dto.BaseResponseDto;
import com.example.analysisreport.core.service.BaseCrudService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * An abstract generic base controller that provides standard CRUD endpoints.
 * Subclasses must provide a BaseCrudService implementation.
 *
 * @param <T> The entity type.
 * @param <I> The type of the entity's identifier.
 * @param <C> The DTO for creating the entity.
 * @param <U> The DTO for updating the entity.
 * @param <R> The DTO for representing the entity as a response, must extend BaseResponseDto.
 */
@Validated
public abstract class BaseCrudController<T, I, C, U, R extends BaseResponseDto<I>> {

    protected final BaseCrudService<T, I, C, U, R> service;

    protected BaseCrudController(BaseCrudService<T, I, C, U, R> service) {
        this.service = service;
    }

    /**
     * Create a new resource.
     * Validates the incoming request body and delegates the creation logic to the service.
     *
     * @param createDto Data Transfer Object containing details for the new resource.
     * @return ResponseEntity containing the created resource, location and HTTP status 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<R> create(@Valid @RequestBody C createDto) {
        R createdResource = service.create(createDto);

        // build location URI for the created resource
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdResource.getId())
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .body(createdResource);
    }

    /**
     * Retrieve a resource by its ID.
     *
     * @param id The identifier of the resource to retrieve.
     * @return ResponseEntity containing the found resource and HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<R> getById(@PathVariable I id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Retrieve all resources.
     *
     * @return ResponseEntity containing a list of all resources and HTTP status 200 OK.
     */
    @GetMapping
    public ResponseEntity<Page<R>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /**
     * Partially update an existing resource by its ID.
     * Validates the incoming request body and delegates the update logic to the service.
     *
     * @param id        The identifier of the resource to update.
     * @param updateDto Data Transfer Object containing updated details for the resource.
     * @return ResponseEntity containing the updated resource and HTTP status 200 OK.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<R> update(@PathVariable I id, @Valid @RequestBody U updateDto) {
        return ResponseEntity.ok(service.update(id, updateDto));
    }

    /**
     * Delete a resource by its ID.
     *
     * @param id The identifier of the resource to delete.
     * @return ResponseEntity with HTTP status 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable I id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
