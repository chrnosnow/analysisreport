package com.example.analysisreport.matrix.repository;

import com.example.analysisreport.matrix.entity.SampleMatrix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampleMatrixRepository extends JpaRepository<SampleMatrix, Long> {
    boolean existsByNameIgnoreCase(String name);

    /**
     * Check if a SampleMatrix with the given name exists, excluding the one with the specified ID.
     * Helpful for the update logic to check if the new name is taken by another matrix.
     *
     * @param name the name to check for uniqueness (case-insensitive)
     * @param id   the ID of the SampleMatrix to exclude from the check
     * @return an Optional containing the found SampleMatrix if it exists, or empty if it does not
     */
    Optional<SampleMatrix> findByNameIgnoreCaseAndIdNot(String name, Long id);
}
