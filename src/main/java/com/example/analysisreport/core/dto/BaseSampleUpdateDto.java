package com.example.analysisreport.core.dto;

import com.example.analysisreport.validation.DateRangeProvider;
import com.example.analysisreport.validation.ValidDateRange;
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
    // we don't allow updating id, sampleCode, clientId, contractId and createdAt to maintain data integrity

    @Size(max = 500, message = "Sample location details cannot exceed 500 characters")
    private String sampleLocationDetails;

    @PastOrPresent(message = "Sampling date cannot be in the future")
    private LocalDateTime samplingDateTime;

    private LocalDateTime receivingDateTime;
}
