package com.example.analysisreport.result.repository;

import com.example.analysisreport.result.entity.AnalysisResult;
import org.springframework.data.repository.CrudRepository;

public interface ResultsRepository extends CrudRepository<AnalysisResult, Long>, CustomResultsRepository {
}
