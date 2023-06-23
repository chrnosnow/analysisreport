package com.example.analysisreport.water_sample.repository;

import com.example.analysisreport.water_sample.entity.WaterSample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WaterSampleRepository extends CrudRepository<WaterSample, Long> {
    List<WaterSample> findAll();

    WaterSample findBySampleCode(String waterSampleCode);

    List<WaterSample> findByClientId(Long clientId);

    List<WaterSample> findByContractId(Long contractId);

    List<WaterSample> findBySampleReceivingDateBetween(Date startDate, Date endDate);
}
