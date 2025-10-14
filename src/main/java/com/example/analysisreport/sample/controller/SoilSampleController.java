package com.example.analysisreport.sample.controller;

import com.example.analysisreport.core.controller.BaseCrudController;
import com.example.analysisreport.core.service.BaseCrudService;
import com.example.analysisreport.sample.dto.SoilSampleCreateDto;
import com.example.analysisreport.sample.dto.SoilSampleResponseDto;
import com.example.analysisreport.sample.dto.SoilSampleUpdateDto;
import com.example.analysisreport.sample.entity.SoilSample;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2/soil-samples")
public class SoilSampleController extends BaseCrudController<SoilSample, Long, SoilSampleCreateDto, SoilSampleUpdateDto, SoilSampleResponseDto> {

    protected SoilSampleController(BaseCrudService<SoilSample, Long, SoilSampleCreateDto, SoilSampleUpdateDto, SoilSampleResponseDto> service) {
        super(service);
    }
}
