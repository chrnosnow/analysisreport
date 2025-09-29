package com.example.analysisreport.results.controller;

import com.example.analysisreport.results.entity.Results;
import com.example.analysisreport.results.repository.ResultsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/results")
public class ResultsController {

    private ResultsRepository resultsRepository;

    public ResultsController(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    @GetMapping("/get/{resultId}")
    public Results getResult(@PathVariable(value = "resultId") Long resultId) {
        Optional<Results> resultOptional = resultsRepository.findById(resultId);
        if (resultOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        return resultOptional.get();
    }

    @PostMapping("/add")
    public Results addResult(@RequestBody Results result) {
        return resultsRepository.save(result);
    }

    @PostMapping("/search")
    //it is important to search the results by the indicator name (not by the indicator id), as there can be multiple
    // indicators with the same name (the analysis method can be different or the indicator can be accredited or not
    // accredited)
    public List<Results> search(@RequestBody ResultsSearchDTO searchRequest) {
        return resultsRepository.customFind(searchRequest);
    }

    @PutMapping("update/{resultId}")
    public Results updateResult(@PathVariable(value = "resultId") Long resultId, @RequestBody String json) throws JsonProcessingException {
        Optional<Results> resultsOptional = resultsRepository.findById(resultId);
        if (resultsOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Results resultToUpdate = resultsOptional.get();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerForUpdating(resultToUpdate);
        resultToUpdate = reader.readValue(json);
        resultsRepository.save(resultToUpdate);
        return resultToUpdate;
    }

    @DeleteMapping("/delete/{resultId}")
    public Results deleteResult(@PathVariable("resultId") Long resultId) {
        Optional<Results> resultsOptional = resultsRepository.findById(resultId);
        if (resultsOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Results resultToDelete = resultsOptional.get();
        resultsRepository.deleteById(resultId);
        return resultToDelete;
    }
}
