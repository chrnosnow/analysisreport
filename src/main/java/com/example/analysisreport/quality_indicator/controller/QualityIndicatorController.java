package com.example.analysisreport.quality_indicator.controller;

import com.example.analysisreport.quality_indicator.entity.QualityIndicator;
import com.example.analysisreport.quality_indicator.repository.QualityIndicatorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/indicators")
public class QualityIndicatorController {
    private QualityIndicatorRepository qiRepository;

    public QualityIndicatorController(QualityIndicatorRepository qiRepository){
        this.qiRepository = qiRepository;
    }

    @GetMapping
    public List<QualityIndicator> getAllQualityIndicators(){
        return qiRepository.findAll();
    }

    @GetMapping("get/{name}")
    public List<QualityIndicator> getByName(@PathVariable(value="name") String name){
        return qiRepository.findByNameContainingIgnoreCase(name);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public QualityIndicator addIndicator(@RequestBody QualityIndicator qualityIndicator){
        if(qualityIndicator.getName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please give a name");
        }

        return qiRepository.save(qualityIndicator);
    }

    @PutMapping("/update/{indicatorId}")
    //can partially update the indicator's details
    //will update only the fields with non-null values
    //will give error on null values
    public QualityIndicator updateIndicator(@PathVariable(value = "indicatorId") Long id, @RequestBody String json) throws JsonProcessingException {
        Optional<QualityIndicator> qiOptional = qiRepository.findById(id);
        if(qiOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        QualityIndicator qiToUpdate = qiOptional.get();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerForUpdating(qiToUpdate);
        qiToUpdate = reader.readValue(json);
        qiRepository.save(qiToUpdate);
        return qiToUpdate;
    }

    @DeleteMapping("delete/{indicatorId}")
    public QualityIndicator deleteIndicator(@PathVariable(value = "indicatorId") Long id){
        Optional<QualityIndicator> qiOptional = qiRepository.findById(id);
        if(qiOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        QualityIndicator qiToDelete = qiOptional.get();
        qiRepository.deleteById(id);
        return qiToDelete;
    }

}
