package com.example.analysisreport.analyst.controller;

import com.example.analysisreport.analyst.entity.Analyst;
import com.example.analysisreport.analyst.repository.AnalystRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/analysts")
public class AnalystController {
    private AnalystRepository analystRepository;

    public AnalystController(AnalystRepository analystRepository) {
        this.analystRepository = analystRepository;
    }

    @GetMapping
    public List<Analyst> getAll() {
        return analystRepository.findAll();
    }

    @GetMapping("/search")
    public List<Analyst> search(@RequestParam String str) {
        return analystRepository.findByDescriptionContaining(str);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Analyst addAnalyst(@RequestBody Analyst analyst) {
        return analystRepository.save(analyst);
    }

    @PutMapping("/update/{analystId}")
    public Analyst updateAnalyst(@PathVariable(value = "analystId") Long analystId, @RequestBody String json) throws JsonProcessingException {
        Optional<Analyst> analystOptional = analystRepository.findById(analystId);
        if (analystOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Analyst analystToUpdate = analystOptional.get();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerForUpdating(analystToUpdate);
        analystToUpdate = reader.readValue(json);

        return analystRepository.save(analystToUpdate);
    }

    @DeleteMapping("/delete/{analystId}")
    public Analyst deleteAnalyst(@PathVariable(value = "analystId") Long analystId) {
        Optional<Analyst> analystOptional = analystRepository.findById(analystId);
        if (analystOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        Analyst analystToDelete = analystOptional.get();
        deleteAnalyst(analystId);

        return analystToDelete;
    }

}
