package com.example.analysisreport.samples.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class SampleSummaryDto {
    private Long id;
    private String sampleCode;
    private Long matrixId;
    private String matrixName;
    private Long clientId;
    private String clientName;
    private Long contractId;
    private String contractCode;
    private LocalDateTime receivingDateTime;
    // TODO
//    private String analysisStatus; // e.g., "Pending", "Completed"
//    private String analysisReportLink; // URL or path to the analysis report
}
