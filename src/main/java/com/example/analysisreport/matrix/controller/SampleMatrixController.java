package com.example.analysisreport.matrix.controller;

import com.example.analysisreport.core.controller.BaseCrudController;
import com.example.analysisreport.matrix.dto.SampleMatrixCreateDto;
import com.example.analysisreport.matrix.dto.SampleMatrixResponseDto;
import com.example.analysisreport.matrix.dto.SampleMatrixUpdateDto;
import com.example.analysisreport.matrix.entity.SampleMatrix;
import com.example.analysisreport.matrix.service.SampleMatrixService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/matrices")
public class SampleMatrixController extends BaseCrudController<SampleMatrix, Long, SampleMatrixCreateDto, SampleMatrixUpdateDto, SampleMatrixResponseDto> {
    public SampleMatrixController(SampleMatrixService sampleMatrixService) {
        super(sampleMatrixService);
    }
}
