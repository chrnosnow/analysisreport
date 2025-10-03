package com.example.analysisreport.samples.controller;


import com.example.analysisreport.samples.dto.WaterSampleCreateDto;
import com.example.analysisreport.samples.dto.WaterSampleResponseDto;
import com.example.analysisreport.samples.service.SampleService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for managing WaterSample entities.
 * Provides endpoints for CRUD operations and specific queries related to water samples.
 * This controller interacts with the SampleService to perform business logic.
 */
@RestController
@RequestMapping("/api/v2/water-samples")
@RequiredArgsConstructor
public class WaterSampleController {

    private final SampleService sampleService;

    /**
     * Endpoint to create a new WaterSample.
     * Validates the incoming request body and delegates the creation logic to the SampleService.
     *
     * @param createDto Data Transfer Object containing details for the new water sample.
     * @return ResponseEntity containing the created WaterSampleResponseDto and HTTP status 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<WaterSampleResponseDto> createWaterSample(@Valid @RequestBody WaterSampleCreateDto createDto) {
        WaterSampleResponseDto responseDto = sampleService.createWaterSample(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * Endpoint to retrieve all WaterSamples.
     *
     * @return ResponseEntity containing a list of WaterSampleResponseDto and HTTP status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<WaterSampleResponseDto>> getAllWaterSamples() {
        List<WaterSampleResponseDto> samples = sampleService.findAllWaterSamples();
        return ResponseEntity.ok(samples);
    }

    /**
     * Endpoint to retrieve a WaterSample by its ID.
     *
     * @param id the ID of the water sample to retrieve
     * @return ResponseEntity containing the WaterSampleResponseDto and HTTP status 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<WaterSampleResponseDto> getWaterSampleById(@PathVariable Long id) {
        WaterSampleResponseDto responseDto = sampleService.findWaterSampleById(id);
        return ResponseEntity.ok(responseDto);
    }

}
