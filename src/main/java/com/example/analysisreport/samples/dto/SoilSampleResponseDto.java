package com.example.analysisreport.samples.dto;

import com.example.analysisreport.core.dto.BaseSampleResponseDto;
import com.example.analysisreport.samples.entity.LandUse;
import com.example.analysisreport.samples.entity.SoilTexture;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
// include fields from BaseSampleResponseDto in equals and hashCode
public class SoilSampleResponseDto extends BaseSampleResponseDto {

    private Double sampleDepthCm;
    private LandUse landUse;
    private SoilTexture soilTexture;
    private String color;
}
