package com.example.analysisreport.water_sample.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "SAMPLES")
public class WaterSample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SAMPLE_CODE", nullable = false)
    private String sampleCode;

    @Column(name = "CLIENT_ID")
    private Long clientId;

    @Column(name = "CONTRACT_ID")
    private Long contractId;

    @Column(name = "WATER_SAMPLE_TYPE")
    private WaterSampleType waterSampleType;

    @Column(name = "SAMPLING_SOURCE")
    private String samplingSource;

    @Column(name = "SAMPLING_DATE")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date samplingDate;

    @Column(name = "SAMPLING_TIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Time samplingTime;

    @Column(name = "SAMPLE_RECEIVING_DATE")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date sampleReceivingDate;

    @Column(name = "SAMPLE_RECEIVING_TIME")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Time receivingTime;
}
