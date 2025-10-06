package com.example.analysisreport.samples.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@AllArgsConstructor
public class SampleSummaryDto {
    private Long id;
    private String sampleCode;
    private String sampleType; // e.g., "Water", "Soil", etc.
    private String clientName;
    private String contractCode;
    private LocalDateTime receivingDateTime;
    // TODO
//    private String analysisStatus; // e.g., "Pending", "Completed"
//    private String analysisReportLink; // URL or path to the analysis report
}
