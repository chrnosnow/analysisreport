package com.example.analysisreport.matrix.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class SampleMatrixUpdateDto {

    @NotBlank(message = "The name is required when updating an existing matrix.")
    private String name;
}
