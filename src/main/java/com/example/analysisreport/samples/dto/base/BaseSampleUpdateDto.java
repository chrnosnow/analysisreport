package com.example.analysisreport.samples.dto.base;

import com.example.analysisreport.core.validation.DateRangeProvider;
import com.example.analysisreport.core.validation.ValidDateRange;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Base DTO for updating sample information.
 * All fields are optional to allow partial updates.
 * The backend service will only update the fields that are not null in this DTO.
 * This class is extended by specific sample type update DTOs.
 */
@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@ValidDateRange(message = "Receiving date must be after or equal to the sampling date")
public abstract class BaseSampleUpdateDto implements DateRangeProvider {
    // we don't allow updating id, sampleCode, matrixName and clientId to maintain data integrity

    private Long contractId;

    @Size(max = 500, message = "Sample location details cannot exceed 500 characters")
    private String sampleLocationDetails;

    @PastOrPresent(message = "Sampling date cannot be in the future")
    private LocalDateTime samplingDateTime;

    private LocalDateTime receivingDateTime;

    @Override
    public String toString() {
        return "sampleLocationDetails: " + sampleLocationDetails +
                ", samplingDateTime: " + samplingDateTime +
                ", receivingDateTime: " + receivingDateTime;
    }
}
