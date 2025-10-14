package com.example.analysisreport.parameter.controller;

import com.example.analysisreport.parameter.entity.AnalysisParameter;
import com.example.analysisreport.parameter.repository.QualityIndicatorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v2/indicators")
public class QualityIndicatorController {
    private QualityIndicatorRepository qiRepository;

    public QualityIndicatorController(QualityIndicatorRepository qiRepository) {
        this.qiRepository = qiRepository;
    }

    @GetMapping
    public List<AnalysisParameter> getAllQualityIndicators() {
        return qiRepository.findAll();
    }

    @GetMapping("get/{name}")
    public List<AnalysisParameter> getByName(@PathVariable(value = "name") String name) {
        return qiRepository.findByNameContainingIgnoreCase(name);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public AnalysisParameter addIndicator(@RequestBody AnalysisParameter analysisParameter) {
        if (analysisParameter.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please give a name");
        }

        return qiRepository.save(analysisParameter);
    }

    @PutMapping("/update/{indicatorId}")
    //can partially update the indicator's details
    //will update only the fields with non-null values
    //will give error on null values
    public AnalysisParameter updateIndicator(@PathVariable(value = "indicatorId") Long id, @RequestBody String json) throws JsonProcessingException {
        Optional<AnalysisParameter> qiOptional = qiRepository.findById(id);
        if (qiOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        AnalysisParameter qiToUpdate = qiOptional.get();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerForUpdating(qiToUpdate);
        qiToUpdate = reader.readValue(json);
        qiRepository.save(qiToUpdate);
        return qiToUpdate;
    }

    @DeleteMapping("delete/{indicatorId}")
    public AnalysisParameter deleteIndicator(@PathVariable(value = "indicatorId") Long id) {
        Optional<AnalysisParameter> qiOptional = qiRepository.findById(id);
        if (qiOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        AnalysisParameter qiToDelete = qiOptional.get();
        qiRepository.deleteById(id);
        return qiToDelete;
    }

}
