package com.example.analysisreport.matrix.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class SampleMatrixCreateDto {

    @NotBlank(message = "The name is required when adding a new matrix.")
    private String name;
}
