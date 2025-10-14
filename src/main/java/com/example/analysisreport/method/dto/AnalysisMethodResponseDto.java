package com.example.analysisreport.method.dto;

import com.example.analysisreport.core.dto.BaseResponseDto;
import lombok.Data;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class AnalysisMethodResponseDto implements BaseResponseDto<Long> {

    private Long id;
    private Long parameterId;
    private String parameterName;
    private String methodReference;
    private Long matrixId;
    private String matrixName;
    private boolean isAccredited;
    private String quantificationLimit;
    private String unit;
    private String description;
    private String createdAt;
    private String updatedAt;

    @Override
    public Long getId() {
        return id;
    }
}
