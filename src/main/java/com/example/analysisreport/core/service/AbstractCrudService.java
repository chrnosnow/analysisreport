package com.example.analysisreport.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractCrudService<T, I, C, U, R> implements BaseCrudService<T, I, C, U, R> {

    // Repository for the entity type T with identifier type I
    // This is used to perform CRUD operations on the entity.
    // It is marked as protected to allow access in subclasses.
    // It is final to ensure it is assigned only once via the constructor.
    // The JpaRepository interface provides standard methods like save, findById, findAll, deleteById, etc.
    // This field is initialized through constructor injection.
    // Subclasses will provide the specific repository implementation.
    protected final JpaRepository<T, I> repository;

    protected AbstractCrudService(JpaRepository<T, I> repository) {
        this.repository = repository;
    }

    /**
     * Finds an entity by its ID and maps it to a response DTO.
     * Or throws a RuntimeException if the entity is not found.
     *
     * @param id the ID of the entity to find
     * @return the response DTO representing the found entity
     * @throws RuntimeException if the entity with the given ID does not exist
     */
    @Override
    @Transactional(readOnly = true)
    public R findById(I id) {
        T entity = findEntityById(id);
        return mapToResponseDto(entity);
    }

    /**
     * Deletes an entity by its ID.
     * Or throws a RuntimeException if the entity is not found.
     *
     * @param id the ID of the entity to delete
     * @throws RuntimeException if the entity with the given ID does not exist
     */
    @Override
    @Transactional
    public void deleteById(I id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException(getResourceName() + " not found with id: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * Retrieves a paginated list of entities and maps them to response DTOs.
     *
     * @param pageable the pagination information
     * @return a page of response DTOs representing the entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<R> findAll(Pageable pageable) {
        Page<T> entityPage = repository.findAll(pageable);
        return entityPage.map(this::mapToResponseDto);
    }

    /**
     * Helper method to find an entity by its ID or throw an exception if not found.
     *
     * @param id the ID of the entity to find
     * @return the found entity
     * @throws RuntimeException if the entity with the given ID does not exist
     */
    protected T findEntityById(I id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(getResourceName() + " not found with id: " + id));
    }

    /**
     * Maps the entity to a response DTO.
     * This method must be implemented by subclasses to provide the specific mapping logic.
     *
     * @param entity the entity to map
     * @return the response DTO representing the entity
     */
    protected abstract R mapToResponseDto(T entity);

    /**
     * Returns the name of the resource for error messages.
     * This method must be implemented by subclasses to provide the specific resource name.
     *
     * @return the name of the resource (e.g., "Water Sample", "Client")
     */
    protected abstract String getResourceName();

}
