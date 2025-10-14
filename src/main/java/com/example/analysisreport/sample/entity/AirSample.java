package com.example.analysisreport.sample.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "air_samples")
// This annotation tells JPA that the primary key of this table (water_samples)
// is also a foreign key to the 'samples' table, linking them together.
@PrimaryKeyJoinColumn(name = "sample_id")
public class AirSample extends Sample {

    @Enumerated(EnumType.STRING)
    @Column(name = "sampling_method", nullable = false)
    private AirSamplingMethod samplingMethod;

    @Column(name = "flow_rate_lpm")
    private Double flowRateLitersPerMinute;

    @Column(name = "duration_minutes")
    private Integer samplingDurationMinutes;

    @Column(name = "filter_type", length = 100)
    private String filterType;
}
