package com.example.analysisreport.samples.dto.base;

import com.example.analysisreport.core.dto.BaseResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    // data for UI display
    private Long matrixId;
    private String matrixName;
    private Long clientId;
    private String clientName;

    private Long contractId;
    private String sampleLocationDetails;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime samplingDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime receivingDateTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // explicitly implement getId() from BaseResponseDto
    @Override
    public Long getId() {
        return id;
    }
}
