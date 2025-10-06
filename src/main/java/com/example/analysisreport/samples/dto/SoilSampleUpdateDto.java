package com.example.analysisreport.samples.dto;

import com.example.analysisreport.core.dto.BaseSampleUpdateDto;
import com.example.analysisreport.samples.entity.LandUse;
import com.example.analysisreport.samples.entity.SoilTexture;
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
