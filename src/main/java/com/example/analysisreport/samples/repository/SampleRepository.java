package com.example.analysisreport.samples.repository;

import com.example.analysisreport.samples.dto.SampleSummaryDto;
import com.example.analysisreport.samples.entity.Sample;
import com.example.analysisreport.samples.entity.SoilSample;
import com.example.analysisreport.samples.entity.WaterSample;
import com.example.analysisreport.samples.entity.WaterSampleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {

    boolean existsBySampleCode(String sampleCode);

    // JPQL query to find all WaterSamples (query against the entity model, not the table)
    @Query("SELECT ws FROM WaterSample ws")
    List<WaterSample> findAllWaterSamples();

    @Query("SELECT ws FROM WaterSample ws WHERE ws.id = :id")
    Optional<WaterSample> findWaterSampleById(Long id);

    @Query("SELECT ws FROM WaterSample ws WHERE ws.type = :type")
    List<WaterSample> findWaterSamplesByType(WaterSampleType type);

    @Query("""
             SELECT new com.example.analysisreport.samples.dto.SampleSummaryDto(
                 s.id,
                 s.sampleCode,
                 CASE TYPE(s)
                     WHEN WaterSample THEN 'Water'
                     WHEN SoilSample THEN 'Soil'
                     WHEN AirSample THEN 'Air'
                     ELSE 'Unknown'
                 END,
                 s.client.name,
                 s.contract.code,
                 s.receivingDateTime
             )
             FROM Sample s
            """)
    List<SampleSummaryDto> findAllSummaries();

    @Query("SELECT ss FROM SoilSample ss")
    List<SoilSample> findAllSoilSamples();

    @Query("SELECT ss FROM SoilSample ss WHERE ss.id = :id")
    Optional<SoilSample> findSoilSampleById(Long id);
}
