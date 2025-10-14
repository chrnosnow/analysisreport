package com.example.analysisreport.method.entity;

import com.example.analysisreport.core.entity.BaseEntity;
import com.example.analysisreport.matrix.entity.SampleMatrix;
import com.example.analysisreport.parameter.entity.AnalysisParameter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "analysis_methods")
@Getter
@NoArgsConstructor
public class AnalysisMethod extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parameter_id", nullable = false)
    private AnalysisParameter parameter;

    @Setter
    @Column(name = "method_reference", length = 100)
    private String methodReference;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matrix_id", nullable = false)
    private SampleMatrix sampleMatrix;

    @Setter
    @Column(name = "accredited", nullable = false)
    private boolean isAccredited = false;

    @Setter
    @Column(name = "quantification_limit", precision = 8, scale = 4)
    private BigDecimal quantificationLimit;

    @Setter
    @Column(name = "unit", length = 50)
    private String unit;

    @Setter
    @Column(name = "description", length = 500)
    private String description;
}
