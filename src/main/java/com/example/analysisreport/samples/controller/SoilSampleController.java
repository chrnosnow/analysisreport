package com.example.analysisreport.samples.controller;

import com.example.analysisreport.samples.dto.SoilSampleCreateDto;
import com.example.analysisreport.samples.dto.SoilSampleResponseDto;
import com.example.analysisreport.samples.service.SampleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/soil-samples")
@RequiredArgsConstructor
public class SoilSampleController {

    private final SampleService sampleService;

    /**
     * Create a new SoilSample.
     * Validates the incoming request body and delegates the creation logic to the SampleService.
     *
     * @param createDto Data Transfer Object containing details for the new soil sample.
     * @return ResponseEntity containing the created SoilSampleResponseDto and HTTP status 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<SoilSampleResponseDto> createSoilSample(@Valid @RequestBody SoilSampleCreateDto createDto) {
        SoilSampleResponseDto responseDto = sampleService.createSoilSample(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<SoilSampleResponseDto>> getAllSoilSamples() {
        List<SoilSampleResponseDto> samples = sampleService.findAllSoilSamples();
        return ResponseEntity.ok(samples);
    }
}
