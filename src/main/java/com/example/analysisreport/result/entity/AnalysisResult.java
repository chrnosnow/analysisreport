package com.example.analysisreport.result.entity;

import com.example.analysisreport.core.entity.BaseEntity;
import com.example.analysisreport.method.entity.AnalysisMethod;
import com.example.analysisreport.sample.entity.Sample;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "analysis_results")
@Getter
@Setter
@NoArgsConstructor
public class AnalysisResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_id", nullable = false)
    private Sample sample;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_id", nullable = false)
    private AnalysisMethod method;

    @Column(name = "result_value", precision = 8, scale = 4)
    private BigDecimal resultValue;

    @Column(name = "reported_as", length = 50)
    private String reportedAs;

    @Column(name = "analysis_date")
    private LocalDate analysisDate;
}
