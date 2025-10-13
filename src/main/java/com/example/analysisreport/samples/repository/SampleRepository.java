package com.example.analysisreport.samples.repository;

import com.example.analysisreport.samples.dto.SampleSummaryDto;
import com.example.analysisreport.samples.entity.Sample;
import com.example.analysisreport.samples.entity.SoilSample;
import com.example.analysisreport.samples.entity.WaterSample;
import com.example.analysisreport.samples.entity.WaterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "SELECT ws FROM WaterSample ws",
            countQuery = "SELECT COUNT(ws) FROM WaterSample ws")
    Page<WaterSample> findAllWaterSamples(Pageable pageable);

    @Query("SELECT ws FROM WaterSample ws WHERE ws.id = :id")
    Optional<WaterSample> findWaterSampleById(Long id);

    @Query("SELECT ws FROM WaterSample ws WHERE ws.type = :type")
    List<WaterSample> findWaterSamplesByType(WaterType type);

    /**
     * Fetches a summary of all samples.
     * JPQL query constructs SampleSummaryDto objects directly from the query results.
     * Uses an explicit LEFT JOIN to include samples without an associated contract.
     * Orders the results by receiving date in descending order.
     *
     * @return List of SampleSummaryDto containing summarized information about each sample.
     */
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
                 s.contract.contractCode,
                 s.receivingDateTime
             )
             FROM Sample s
             LEFT JOIN s.contract c
             ORDER BY s.receivingDateTime DESC
            """)
    List<SampleSummaryDto> findAllSummaries();

    @Query("SELECT ss FROM SoilSample ss")
    List<SoilSample> findAllSoilSamples();

    @Query(value = "SELECT ss FROM SoilSample ss",
            countQuery = "SELECT COUNT(ss) FROM SoilSample ss")
    Page<SoilSample> findAllSoilSamples(Pageable pageable);

    @Query("SELECT ss FROM SoilSample ss WHERE ss.id = :id")
    Optional<SoilSample> findSoilSampleById(Long id);

}
