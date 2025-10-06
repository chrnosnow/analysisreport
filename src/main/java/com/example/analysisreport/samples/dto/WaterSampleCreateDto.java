package com.example.analysisreport.samples.dto;

import com.example.analysisreport.core.dto.BaseSampleCreateDto;
import com.example.analysisreport.samples.entity.WaterSampleType;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
// include fields from BaseSampleCreateDto in equals and hashCode
public class WaterSampleCreateDto extends BaseSampleCreateDto {

    @NotNull(message = "Water sample type is required")
    private WaterSampleType waterSampleType;
}
