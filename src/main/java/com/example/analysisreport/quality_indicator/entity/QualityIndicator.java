package com.example.analysisreport.quality_indicator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "WATER_QUALITY_INDICATORS")
public class QualityIndicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "MU")
    private String measurementUnit;

    @Column(name = "ACCREDITED")
    private AccreditedStatus accredited;

    @Column(name = "METHOD_REFERENCE")
    private String methodReference;
}
