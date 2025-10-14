package com.example.analysisreport.sample.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "soil_samples")
// This annotation tells JPA that the primary key of this table (water_samples)
// is also a foreign key to the 'samples' table, linking them together.
@PrimaryKeyJoinColumn(name = "sample_id")
public class SoilSample extends Sample {

    @Column(name = "sampling_depth_centimeters")
    private Double samplingDepthCentimeters;

    @Enumerated(EnumType.STRING)
    @Column(name = "soil_texture")
    private SoilTexture soilTexture;

    @Column(name = "color", length = 50)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "land_use", length = 100)
    private LandUse landUse;
}
