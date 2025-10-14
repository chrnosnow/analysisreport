package com.example.analysisreport.sample.dto;

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
    private String matrixName;
    private String clientName;
    private String contractCode;
    private LocalDateTime receivingDateTime;
    // TODO
//    private String analysisStatus; // e.g., "Pending", "Completed"
//    private String analysisReportLink; // URL or path to the analysis report
}
