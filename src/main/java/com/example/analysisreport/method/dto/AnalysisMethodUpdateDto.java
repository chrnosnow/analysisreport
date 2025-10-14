package com.example.analysisreport.method.dto;

import lombok.Data;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class AnalysisMethodUpdateDto {

    private Long parameterId;
    private String methodReference;
    private Long matrixId;
    private boolean isAccredited;
    private String quantificationLimit;
    private String unit;
    private String description;
}
