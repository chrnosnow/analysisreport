package com.example.analysisreport.samples.dto;

import com.example.analysisreport.samples.entity.LandUse;
import com.example.analysisreport.samples.entity.SoilTexture;
import com.example.analysisreport.samples.entity.WaterSampleType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SoilSampleResponseDto {
    private Long id;
    private String sampleCode;
    private Long contractId;

    // data for UI display
    private Long clientId;
    private String clientName;

    private String sampleLocationDetails;
    private Double sampleDepthCm;
    private LandUse landUse;
    private SoilTexture soilTexture;
    private String color;
    private LocalDateTime samplingDateTime;
    private LocalDateTime receivingDateTime;
    private LocalDateTime createdAt;
}
