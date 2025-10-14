package com.example.analysisreport.sample.entity;

import com.example.analysisreport.matrix.entity.SampleMatrix;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "water_samples")
// This annotation tells JPA that the primary key of this table (water_samples)
// is also a foreign key to the 'samples' table, linking them together.
@PrimaryKeyJoinColumn(name = "sample_id")
public class WaterSample extends Sample {

    // EnumType.STRING ensures that the enum is stored as a string in the database
    @Enumerated(EnumType.STRING)
    @Column(name = "water_sample_type")
    private WaterType type;

    public WaterSample(String sampleCode, SampleMatrix matrix) {
        super(sampleCode, matrix);
    }

    @Override
    public String toString() {
        return super.toString() + ", type: " + type;
    }
}
