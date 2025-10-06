package com.example.analysisreport.samples.dto;

import com.example.analysisreport.core.dto.BaseSampleResponseDto;
import com.example.analysisreport.samples.entity.WaterSampleType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
// include fields from BaseSampleResponseDto in equals and hashCode
public class WaterSampleResponseDto extends BaseSampleResponseDto {

    private WaterSampleType waterSampleType;

}
