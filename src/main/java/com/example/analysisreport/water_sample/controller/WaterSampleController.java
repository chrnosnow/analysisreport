package com.example.analysisreport.water_sample.controller;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.client.repository.ClientRepository;
import com.example.analysisreport.water_sample.entity.WaterSample;
import com.example.analysisreport.water_sample.repository.WaterSampleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/water-samples")
public class WaterSampleController {

    private WaterSampleRepository waterSampleRepository;
    private ClientRepository clientRepository;

    public WaterSampleController(WaterSampleRepository waterSampleRepository, ClientRepository clientRepository) {
        this.waterSampleRepository = waterSampleRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public List<WaterSample> getAllSamples() {
        return this.waterSampleRepository.findAll();
    }

    @GetMapping("/search/{clientId}")
    public List<WaterSample> getByClientId(@PathVariable(value = "clientId") Long clientId) {
        return waterSampleRepository.findByClientId(clientId);
    }

    @GetMapping("/search/{contractId}")
    public List<WaterSample> getByContractId(@PathVariable(value = "contractId") Long contractId) {
        return waterSampleRepository.findByContractId(contractId);
    }

    @GetMapping("/search")
    public List<WaterSample> searchByDateBetween(@RequestParam(name = "startDate") @DateTimeFormat(pattern = "dd.MM" +
            ".yyyy") Date startDate,
                                                 @RequestParam(name = "endDate") @DateTimeFormat(pattern = "dd.MM" +
                                                         ".yyyy") Date endDate) {
        List<WaterSample> result = null;
        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            result = waterSampleRepository.findBySampleReceivingDateBetween(startDate, endDate);
        }

        return result;
    }

    @PostMapping("/add/{clientId}")
    @ResponseStatus(HttpStatus.CREATED)
    public WaterSample addSample(@PathVariable(value = "clientId") Long clientId,
                                 @RequestBody WaterSample waterSample) {

        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        waterSample.setClientId(clientId);
        return waterSampleRepository.save(waterSample);
    }

    @PutMapping("/update/{sampleId}")
    //can partially update the sample details
    //will update only the fields with non-null values
    //will give error on null values
    public WaterSample updateSample(@PathVariable(value = "sampleId") Long sampleId, @RequestBody String json) throws JsonProcessingException {
        Optional<WaterSample> sampleOptional = waterSampleRepository.findById(sampleId);
        if (sampleOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        WaterSample sampleToUpdate = sampleOptional.get();
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerForUpdating(sampleToUpdate);
        sampleToUpdate = reader.readValue(json);
        waterSampleRepository.save(sampleToUpdate);
        return sampleToUpdate;
    }

    @DeleteMapping("/delete/{sampleId}")
    public WaterSample deleteBySampleId(@PathVariable(value = "sampleId") Long sampleId) {
        Optional<WaterSample> sampleOptional = waterSampleRepository.findById(sampleId);
        if (sampleOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }

        WaterSample sampleToDelete = sampleOptional.get();
        waterSampleRepository.deleteById(sampleId);
        return sampleToDelete;
    }

}
