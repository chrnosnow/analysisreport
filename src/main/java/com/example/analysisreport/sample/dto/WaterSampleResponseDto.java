package com.example.analysisreport.sample.dto;

import com.example.analysisreport.sample.dto.base.BaseSampleResponseDto;
import com.example.analysisreport.sample.entity.WaterType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
// include fields from BaseSampleResponseDto in equals and hashCode
public class WaterSampleResponseDto extends BaseSampleResponseDto {

    private WaterType waterType;

}
