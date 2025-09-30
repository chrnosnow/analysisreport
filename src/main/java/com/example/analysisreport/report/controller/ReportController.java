package com.example.analysisreport.report.controller;

import com.example.analysisreport.report.entity.Report;
import com.example.analysisreport.report.repository.ReportRepository;
import com.example.analysisreport.report.search.ReportSearchDTO;
import com.example.analysisreport.report.search.ReportSpecificationsBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v2/reports")
public class ReportController {
    private ReportRepository reportRepository;

    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping("/get/all")
    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    @GetMapping("/get/{sampleId}")
    public Report getBySampleId(@PathVariable(value = "sampleId") Long sampleId) {
        Optional<Report> reportOptional = reportRepository.findByWaterSampleId(sampleId);
        if (reportOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        return reportOptional.get();
    }

    @PostMapping("/add")
    public Report addReport(@RequestBody Report report) {
        return reportRepository.save(report);
    }

    @PostMapping("/search")
    public List<Report> search(@RequestBody ReportSearchDTO searchRequest) {
        ReportSpecificationsBuilder builder = new ReportSpecificationsBuilder();
        if (searchRequest.getStartDate() != null) {
            builder.with("issueDate", ">", searchRequest.getStartDate());
        }

        if (searchRequest.getEndDate() != null) {
            builder.with("issueDate", "<", searchRequest.getEndDate());
        }

        if (searchRequest.getAnalystId() != null) {
            builder.with("analystId", ":", searchRequest.getAnalystId());
        }

        if (searchRequest.getClientId() != null) {
            builder.with("clientId", ":", searchRequest.getClientId());
        }

        if (searchRequest.getContractId() != null) {
            builder.with("contractId", ":", searchRequest.getContractId());
        }

        Specification<Report> spec = builder.build();
        return reportRepository.findAll(spec);
    }

    @PutMapping("/update/{reportId}")
    //can partially update the analysis report's details
    //will update only the fields with non-null values
    //will give error on null values
    public Report updateReport(@PathVariable(value = "reportId") Long reportId, @RequestBody String json) throws JsonProcessingException {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Report reportToUpdate = reportOptional.get();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerForUpdating(reportToUpdate);
        reportToUpdate = reader.readValue(json);
        reportRepository.save(reportToUpdate);
        return reportToUpdate;
    }

    @DeleteMapping("/delete/{reportId}")
    public Report deleteReport(@PathVariable(value = "reportId") Long reportId) {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Report reportToDelete = reportOptional.get();
        reportRepository.deleteById(reportId);
        return reportToDelete;
    }
}
