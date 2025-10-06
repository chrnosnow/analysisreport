package com.example.analysisreport.samples.controller;

import com.example.analysisreport.core.controller.BaseCrudController;
import com.example.analysisreport.core.service.BaseCrudService;
import com.example.analysisreport.samples.dto.SoilSampleCreateDto;
import com.example.analysisreport.samples.dto.SoilSampleResponseDto;
import com.example.analysisreport.samples.dto.SoilSampleUpdateDto;
import com.example.analysisreport.samples.entity.SoilSample;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2/soil-samples")
public class SoilSampleController extends BaseCrudController<SoilSample, Long, SoilSampleCreateDto, SoilSampleUpdateDto, SoilSampleResponseDto> {

    protected SoilSampleController(BaseCrudService<SoilSample, Long, SoilSampleCreateDto, SoilSampleUpdateDto, SoilSampleResponseDto> service) {
        super(service);
    }
}
