package com.example.analysisreport.samples.dto;

import com.example.analysisreport.core.dto.BaseSampleUpdateDto;
import com.example.analysisreport.samples.entity.WaterSampleType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO for updating an existing Water Sample.
 * All fields are optional to allow partial updates.
 * The backend service will only update the fields that are not null in this DTO
 */
@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
// include fields from BaseSampleUpdateDto in equals and hashCode
public class WaterSampleUpdateDto extends BaseSampleUpdateDto {

    private WaterSampleType waterSampleType;

    @Override
    public String toString() {
        return super.toString() + ", waterSampleType: " + waterSampleType;
    }
}
