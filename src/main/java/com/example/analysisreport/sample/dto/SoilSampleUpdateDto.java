package com.example.analysisreport.sample.dto;

import com.example.analysisreport.sample.dto.base.BaseSampleUpdateDto;
import com.example.analysisreport.sample.entity.LandUse;
import com.example.analysisreport.sample.entity.SoilTexture;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
// include fields from BaseSampleUpdateDto in equals and hashCode
public class SoilSampleUpdateDto extends BaseSampleUpdateDto {

    private Double sampleDepthCm;
    private SoilTexture soilTexture;
    private String color;
    private LandUse landUse;
}
