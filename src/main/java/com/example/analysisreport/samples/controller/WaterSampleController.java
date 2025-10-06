package com.example.analysisreport.samples.controller;


import com.example.analysisreport.core.controller.BaseCrudController;
import com.example.analysisreport.samples.dto.WaterSampleCreateDto;
import com.example.analysisreport.samples.dto.WaterSampleResponseDto;
import com.example.analysisreport.samples.dto.WaterSampleUpdateDto;
import com.example.analysisreport.samples.entity.WaterSample;
import com.example.analysisreport.samples.service.WaterSampleService;

import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing WaterSample entities.
 * Provides endpoints for CRUD operations and specific queries related to water samples.
 * This controller interacts with the SampleService to perform business logic.
 */
@RestController
@RequestMapping("/api/v2/water-samples")
public class WaterSampleController extends BaseCrudController<WaterSample, Long, WaterSampleCreateDto, WaterSampleUpdateDto, WaterSampleResponseDto> {

    public WaterSampleController(WaterSampleService waterSampleService) {
        super(waterSampleService);
    }
}
