package com.example.analysisreport.samples.dto;

import com.example.analysisreport.samples.entity.WaterSampleType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WaterSampleResponseDto {
    private Long id;
    private String sampleCode;
    private WaterSampleType waterSampleType;
    private Long contractId;

    // data for UI display
    private Long clientId;
    private String clientName;

    private String sampleLocationDetails;
    private LocalDateTime samplingDateTime;
    private LocalDateTime receivingDateTime;
    private LocalDateTime createdAt;
}
