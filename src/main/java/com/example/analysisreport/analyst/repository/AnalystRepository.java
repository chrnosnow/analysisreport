package com.example.analysisreport.analyst.repository;

import com.example.analysisreport.analyst.entity.Analyst;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalystRepository extends CrudRepository<Analyst, Long> {

    List<Analyst> findAll();

    List<Analyst> findByDescriptionContaining(String str);
}
