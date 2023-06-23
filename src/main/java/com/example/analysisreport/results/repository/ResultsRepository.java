package com.example.analysisreport.results.repository;

import com.example.analysisreport.results.entity.Results;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ResultsRepository extends CrudRepository<Results, Long>, CustomResultsRepository {
}
