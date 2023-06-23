package com.example.analysisreport.results.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultsSearchDTO {
    private Long sampleId;
    private Long reportId;
    private String qualityIndicatorName;
}
