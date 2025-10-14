package com.example.analysisreport.method.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class AnalysisMethodCreateDto {

    @NotBlank(message = "A parameter ID is required to create an analysis method.")
    private Long parameterId;

    @NotBlank(message = "A method ID is required to create an analysis method.")
    private String methodReference;

    @NotNull(message = "A matrix ID is required to create an analysis method.")
    private Long matrixId;

    private boolean isAccredited;

    private String quantificationLimit;

    private String unit;

    private String description;
}
