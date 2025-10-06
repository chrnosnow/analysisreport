package com.example.analysisreport.samples.controller;

import com.example.analysisreport.samples.dto.SampleSummaryDto;
import com.example.analysisreport.samples.service.SampleService;
import com.example.analysisreport.samples.service.SampleValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/samples")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    /**
     * Endpoint to retrieve summaries of all samples, regardless of their type.
     *
     * @return ResponseEntity containing a list of SampleSummaryDto and HTTP status 200 OK.
     */
    @GetMapping("/summary")
    public ResponseEntity<List<SampleSummaryDto>> getAllSampleSummaries() {
        List<SampleSummaryDto> summaries = sampleService.getAllSampleSummaries();
        return ResponseEntity.ok(summaries);
    }
}
