package com.example.analysisreport.samples.service;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.core.service.BaseCrudService;
import com.example.analysisreport.exception.DuplicateResourceException;
import com.example.analysisreport.exception.InvalidRequestException;
import com.example.analysisreport.exception.ResourceNotFound;
import com.example.analysisreport.samples.dto.*;
import com.example.analysisreport.samples.entity.SoilSample;
import com.example.analysisreport.samples.entity.WaterSample;
import com.example.analysisreport.samples.mapper.SampleMapper;
import com.example.analysisreport.samples.repository.SampleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoilSampleService implements BaseCrudService<SoilSample, Long, SoilSampleCreateDto, SoilSampleUpdateDto, SoilSampleResponseDto> {

    private final SampleRepository sampleRepository;
    private final SampleMapper sampleMapper;
    private final SampleValidationService validationService;

    /**
     * Creates a new SoilSample based on the provided DTO.
     * Validates the existence of the associated Client and Contract,
     * and ensures the sample code is unique before persisting.
     *
     * @param dto Data Transfer Object containing details for the new soil sample.
     * @return SoilSampleResponseDto containing details of the created soil sample.
     * @throws ResourceNotFound           if the client or contract with the given IDs do not exist
     * @throws InvalidRequestException    if the contract does not belong to the specified client
     * @throws DuplicateResourceException if a sample with the given code already exists
     */
    @Transactional // ensures that the operation is atomic
    public SoilSampleResponseDto create(@Valid SoilSampleCreateDto dto) {
        Client client = validationService.loadClient(dto.getClientId());
        Contract contract = validationService.loadAndValidateContract(dto.getContractId(), client);
        // map DTO to Entity
        SoilSample soilSampleEntity = sampleMapper.toEntity(dto);
        // persist entity to database
        SoilSample persistedEntity = validationService.persistSample(soilSampleEntity, dto.getSampleCode(), client, contract);
        // map Entity to Response DTO
        return sampleMapper.toDto(persistedEntity);
    }

    /**
     * Retrieves all SoilSamples from the database and maps them to DTOs.
     *
     * @return List of SoilSampleResponseDto representing all soil samples.
     */
    @Override
    public Page<SoilSampleResponseDto> findAll(Pageable pageable) {
        Page<SoilSample> entityPage = sampleRepository.findAllSoilSamples(pageable);
        return entityPage.map(sampleMapper::toDto);
    }

    @Override
    public SoilSampleResponseDto update(Long id, SoilSampleUpdateDto updateDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    /**
     * Retrieves a SoilSample by its ID and maps it to a DTO.
     *
     * @param id the ID of the soil sample to retrieve
     * @return SoilSampleResponseDto representing the soil sample
     * @throws ResourceNotFound if the soil sample with the given ID does not exist
     */
    public SoilSampleResponseDto findById(Long id) {
        Optional<SoilSample> optionalSoilSample = sampleRepository.findSoilSampleById(id);
        return optionalSoilSample.map(sampleMapper::toDto) // If the optional has a value, map it to a DTO.
                .orElseThrow(() -> new ResourceNotFound("Soil Sample not found with id: " + id)); // If not, throw an exception.
    }

}
