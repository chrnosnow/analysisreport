package com.example.analysisreport.client.dto;

import com.example.analysisreport.core.dto.BaseResponseDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class ClientResponseDto implements BaseResponseDto<Long> {
    private Long id;
    private String clientCode;
    private String name;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public Long getId() {
        return id;
    }
}
