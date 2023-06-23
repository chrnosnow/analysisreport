package com.example.analysisreport.report.repository;

import com.example.analysisreport.report.entity.Report;
import com.example.analysisreport.water_sample.entity.WaterSample;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long>, JpaSpecificationExecutor<Report> {
    List<Report> findAll();

    List<Report> findAll(Specification<Report> specification);

    Optional<Report> findByWaterSampleId(Long waterSampleId);
}
