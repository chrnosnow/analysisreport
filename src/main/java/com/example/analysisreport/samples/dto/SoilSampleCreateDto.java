package com.example.analysisreport.samples.dto;

import com.example.analysisreport.samples.entity.LandUse;
import com.example.analysisreport.samples.entity.SoilTexture;
import com.example.analysisreport.samples.entity.WaterSampleType;
import com.example.analysisreport.validation.DateRangeProvider;
import com.example.analysisreport.validation.ValidDateRange;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@ValidDateRange(message = "Receiving date must be after or equal to the sampling date")
// custom annotation to validate date range
public class SoilSampleCreateDto implements DateRangeProvider {
    @NotBlank(message = "Sample code cannot be blank")
    private String sampleCode;

    @NotNull(message = "Client ID is required")
    private Long clientId;

    private Long contractId; // optional field

    @Size(max = 500, message = "Sample location details cannot exceed 500 characters")
    private String sampleLocationDetails;

    @NotNull(message = "Sampling date and time is required")
    @PastOrPresent
    private LocalDateTime samplingDateTime;

    @NotNull(message = "Receiving date and time is required")
    private LocalDateTime receivingDateTime;

    private Double sampleDepthCm;
    private SoilTexture soilTexture;
    private String color;
    private LandUse landUse;
}
