package com.example.analysisreport.sample.dto;

import com.example.analysisreport.sample.dto.base.BaseSampleCreateDto;
import com.example.analysisreport.sample.entity.LandUse;
import com.example.analysisreport.sample.entity.SoilTexture;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
// include fields from BaseSampleCreateDto in equals and hashCode
public class SoilSampleCreateDto extends BaseSampleCreateDto {

    private Double sampleDepthCm;
    private SoilTexture soilTexture;
    private String color;
    private LandUse landUse;
}
