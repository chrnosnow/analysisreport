package com.example.analysisreport.results.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "RESULTS")
@Getter
@Setter
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SAMPLE_ID", nullable = false)
    private Long sampleId;

    @Column(name = "REPORT_ID")
    private Long reportId;

    @Column(name = "QUALITY_INDICATOR_ID", nullable = false)
    private Long qualityIndicatorId;

    @Column(name = "RESULT")
    private String result;
}
