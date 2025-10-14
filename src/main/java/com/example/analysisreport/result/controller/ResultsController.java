package com.example.analysisreport.result.controller;

import com.example.analysisreport.result.entity.AnalysisResult;
import com.example.analysisreport.result.repository.ResultsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v2/results")
public class ResultsController {

    private ResultsRepository resultsRepository;

    public ResultsController(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    @GetMapping("/get/{resultId}")
    public AnalysisResult getResult(@PathVariable(value = "resultId") Long resultId) {
        Optional<AnalysisResult> resultOptional = resultsRepository.findById(resultId);
        if (resultOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        return resultOptional.get();
    }

    @PostMapping("/add")
    public AnalysisResult addResult(@RequestBody AnalysisResult result) {
        return resultsRepository.save(result);
    }

    @PostMapping("/search")
    //it is important to search the results by the indicator name (not by the indicator id), as there can be multiple
    // indicators with the same name (the analysis method can be different or the indicator can be accredited or not
    // accredited)
    public List<AnalysisResult> search(@RequestBody ResultsSearchDTO searchRequest) {
        return resultsRepository.customFind(searchRequest);
    }

    @PutMapping("update/{resultId}")
    public AnalysisResult updateResult(@PathVariable(value = "resultId") Long resultId, @RequestBody String json) throws JsonProcessingException {
        Optional<AnalysisResult> resultsOptional = resultsRepository.findById(resultId);
        if (resultsOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        AnalysisResult resultToUpdate = resultsOptional.get();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerForUpdating(resultToUpdate);
        resultToUpdate = reader.readValue(json);
        resultsRepository.save(resultToUpdate);
        return resultToUpdate;
    }

    @DeleteMapping("/delete/{resultId}")
    public AnalysisResult deleteResult(@PathVariable("resultId") Long resultId) {
        Optional<AnalysisResult> resultsOptional = resultsRepository.findById(resultId);
        if (resultsOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        AnalysisResult resultToDelete = resultsOptional.get();
        resultsRepository.deleteById(resultId);
        return resultToDelete;
    }
}
