package com.example.analysisreport.sample.dto.base;

import com.example.analysisreport.core.validation.DateRangeProvider;
import com.example.analysisreport.core.validation.ValidDateRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * An abstract base class for all Sample Create DTOs.
 * It contains the common fields and validation required to create any type of sample.
 * It also implements the necessary contracts for validation and generic service processing.
 */
@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@ValidDateRange(message = "Receiving date must be after or equal to the sampling date")
// custom annotation to validate date range
public abstract class BaseSampleCreateDto implements DateRangeProvider {

    @NotBlank(message = "Sample code cannot be blank when creating a sample")
    private String sampleCode;

    @NotNull(message = "Matrix ID cannot be blank when adding a sample")
    private Long matrixId;

    @NotNull(message = "Client ID is required")
    private Long clientId;

    private Long contractId; // optional field

    @Size(max = 500, message = "Sample location details cannot exceed 500 characters")
    private String sampleLocationDetails;

    @NotNull(message = "Sampling date and time is required")
    @PastOrPresent(message = "Sampling date cannot be in the future")
    private LocalDateTime samplingDateTime;

    @NotNull(message = "Receiving date and time is required")
    private LocalDateTime receivingDateTime;
}
