package com.example.analysisreport.result.repository;

import com.example.analysisreport.result.controller.ResultsSearchDTO;
import com.example.analysisreport.result.entity.AnalysisResult;

import java.util.List;

public interface CustomResultsRepository {
    List<AnalysisResult> customFind(ResultsSearchDTO searchRequest);
}
