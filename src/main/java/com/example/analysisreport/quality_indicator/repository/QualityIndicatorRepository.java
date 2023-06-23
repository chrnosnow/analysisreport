package com.example.analysisreport.quality_indicator.repository;

import com.example.analysisreport.quality_indicator.entity.QualityIndicator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualityIndicatorRepository extends CrudRepository<QualityIndicator, Long> {

    List<QualityIndicator> findAll();

    List<QualityIndicator> findByNameContainingIgnoreCase(String name);
}
