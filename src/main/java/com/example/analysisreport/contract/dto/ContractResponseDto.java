package com.example.analysisreport.contract.dto;

import com.example.analysisreport.contract.entity.ContractType;
import com.example.analysisreport.core.dto.BaseResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class ContractResponseDto implements BaseResponseDto<Long> {
    private Long id;
    private String contractCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate contractDate;

    // data for UI display
    private Long clientId;
    private String clientName;

    private ContractType contractType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // explicitly implement getId() from BaseResponseDto
    @Override
    public Long getId() {
        return id;
    }
}
