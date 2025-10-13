package com.example.analysisreport.matrix.entity;

import com.example.analysisreport.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity describing the environmental or material matrix of a sample — the substance or medium (e.g., soil, water, air, sediment, waste)
 * in which the sample exists and from which analytical measurements are derived.
 */
@Entity
@Getter
@Table(name = "matrices")
@NoArgsConstructor
public class SampleMatrix extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
}
