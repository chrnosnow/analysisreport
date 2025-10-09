package com.example.analysisreport.samples.dto;

import com.example.analysisreport.samples.dto.base.BaseSampleCreateDto;
import com.example.analysisreport.samples.entity.LandUse;
import com.example.analysisreport.samples.entity.SoilTexture;
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
