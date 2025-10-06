package com.example.analysisreport.core.service;

import java.util.List;

/**
 * A generic interface for basic CRUD (Create, Read, Update, Delete) operations.
 *
 * @param <T> The entity type.
 * @param <I> The type of the entity's identifier.
 * @param <C> The DTO for creating the entity.
 * @param <U> The DTO for updating the entity.
 * @param <R> The DTO for representing the entity as a response.
 */
public interface BaseCrudService<T, I, C, U, R> {
    R create(C createDto);

    R findById(I id);

    List<R> findAll();

    R update(I id, U updateDto);

    void deleteById(I id);
}
