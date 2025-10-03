package com.example.analysisreport.samples.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class SampleSummaryDto {
    private Long id;
    private String sampleCode;
    private String sampleCategory; // e.g., "Water", "Soil", etc.
    private String clientName;
    private String contractCode;
    private LocalDateTime receivingDateTime;
    // TODO
//    private String analysisStatus; // e.g., "Pending", "Completed"
//    private String analysisReportLink; // URL or path to the analysis report
}
