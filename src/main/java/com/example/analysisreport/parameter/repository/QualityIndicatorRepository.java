package com.example.analysisreport.parameter.repository;

import com.example.analysisreport.parameter.entity.AnalysisParameter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualityIndicatorRepository extends CrudRepository<AnalysisParameter, Long> {

    List<AnalysisParameter> findAll();

    List<AnalysisParameter> findByNameContainingIgnoreCase(String name);
}
