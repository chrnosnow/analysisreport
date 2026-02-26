package com.example.analysisreport.sample.entity;

import com.example.analysisreport.matrix.entity.SampleMatrix;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "waste_samples")
// This annotation tells JPA that the primary key of this table (water_samples)
// is also a foreign key to the 'samples' table, linking them together.
@PrimaryKeyJoinColumn(name = "sample_id")
public class WasteSample extends Sample {

    // physical state of the waste: solid, liquid, sludge
    @Enumerated(EnumType.STRING)
    @Column(name = "waste_type", nullable = false)
    private WasteType wasteType;

    @Column(name = "is_hazardous", nullable = false)
    private boolean isHazardous;

    public WasteSample(String sampleCode, SampleMatrix matrix) {
        super(sampleCode, matrix);
    }

}
