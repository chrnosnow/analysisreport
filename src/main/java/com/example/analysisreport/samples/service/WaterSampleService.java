package com.example.analysisreport.samples.service;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.client.repository.ClientRepository;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.contract.repository.ContractRepository;
import com.example.analysisreport.core.service.BaseCrudService;
import com.example.analysisreport.exception.DuplicateResourceException;
import com.example.analysisreport.exception.InvalidRequestException;
import com.example.analysisreport.exception.ResourceNotFound;
import com.example.analysisreport.samples.dto.*;
import com.example.analysisreport.samples.entity.Sample;
import com.example.analysisreport.samples.entity.SoilSample;
import com.example.analysisreport.samples.entity.WaterSample;
import com.example.analysisreport.samples.mapper.SampleMapper;
import com.example.analysisreport.samples.repository.SampleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WaterSampleService implements BaseCrudService<WaterSample, Long, WaterSampleCreateDto, WaterSampleUpdateDto, WaterSampleResponseDto> {

    private final SampleRepository sampleRepository;
    private final SampleMapper sampleMapper;
    private final SampleValidationService validationService;

    /**
     * Creates a new WaterSample based on the provided DTO.
     * Validates the existence of the associated Client and Contract,
     * and ensures the sample code is unique before persisting.
     *
     * @param dto Data Transfer Object containing details for the new water sample.
     * @return WaterSampleResponseDto containing details of the created water sample.
     * @throws ResourceNotFound           if the client or contract with the given IDs do not exist
     * @throws InvalidRequestException    if the contract does not belong to the specified client
     * @throws DuplicateResourceException if a sample with the given code already exists
     */
    @Override
    @Transactional // ensures that the operation is atomic
    public WaterSampleResponseDto create(WaterSampleCreateDto dto) {
        Client client = validationService.loadClient(dto.getClientId());
        Contract contract = validationService.loadAndValidateContract(dto.getContractId(), client);
        // map DTO to Entity
        WaterSample waterSampleEntity = sampleMapper.toEntity(dto);
        // persist the sample
        WaterSample persistedEntity = validationService.persistSample(waterSampleEntity, dto.getSampleCode(), client, contract);
        // map Entity to Response DTO
        return sampleMapper.toDto(persistedEntity);
    }

    /**
     * Retrieves all WaterSamples from the database and maps them to DTOs.
     *
     * @return List of WaterSampleResponseDto representing all water samples.
     */
    public List<WaterSampleResponseDto> findAll() {
        List<WaterSample> samples = sampleRepository.findAllWaterSamples();
        return samples.stream()
                .map(sampleMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a WaterSample by its ID and maps it to a DTO.
     *
     * @param id the ID of the water sample to retrieve
     * @return WaterSampleResponseDto representing the water sample
     * @throws ResourceNotFound if the water sample with the given ID does not exist
     */
    public WaterSampleResponseDto findById(Long id) {
        Optional<WaterSample> optionalWaterSample = sampleRepository.findWaterSampleById(id);
        return optionalWaterSample.map(sampleMapper::toDto) // If the optional has a value, map it to a DTO.
                .orElseThrow(() -> new ResourceNotFound("Water Sample not found with id: " + id)); // If not, throw an exception.
    }

    @Override
    public WaterSampleResponseDto update(Long id, WaterSampleUpdateDto updateDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
