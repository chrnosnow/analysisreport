package com.example.analysisreport.sample.dto;

import com.example.analysisreport.sample.dto.base.BaseSampleResponseDto;
import com.example.analysisreport.sample.entity.LandUse;
import com.example.analysisreport.sample.entity.SoilTexture;
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
