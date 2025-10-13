package com.example.analysisreport.matrix.dto;

import com.example.analysisreport.core.dto.BaseResponseDto;
import lombok.Data;

import java.time.LocalDateTime;


@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class SampleMatrixResponseDto implements BaseResponseDto<Long> {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
