package com.example.analysisreport.parameter.entity;

import com.example.analysisreport.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "analysis_parameters")
public class AnalysisParameter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "measurement_unit")
    private String measurementUnit;

    public AnalysisParameter(String name, String units) {
        this.name = name;
        this.measurementUnit = units;
    }
}
