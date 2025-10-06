package com.example.analysisreport.core.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * An abstract base class for all Sample Response DTOs.
 * Contains the common fields returned for any type of sample.
 */
@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public abstract class BaseSampleResponseDto implements BaseResponseDto<Long> {
    private Long id;
    private String sampleCode;
    private Long contractId;

    // data for UI display
    private Long clientId;
    private String clientName;

    private String sampleLocationDetails;
    private LocalDateTime samplingDateTime;
    private LocalDateTime receivingDateTime;
    private LocalDateTime createdAt;

    // explicitly implement getId() from BaseResponseDto
    @Override
    public Long getId() {
        return id;
    }
}
