package com.example.analysisreport.results.repository;

import com.example.analysisreport.results.controller.ResultsSearchDTO;
import com.example.analysisreport.results.entity.Results;

import java.util.List;

public interface CustomResultsRepository {
    List<Results> customFind(ResultsSearchDTO searchRequest);
}
