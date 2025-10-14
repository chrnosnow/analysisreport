package com.example.analysisreport.sample.service;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.core.service.AbstractSampleService;
import com.example.analysisreport.core.service.ValidationService;
import com.example.analysisreport.exception.DuplicateResourceException;
import com.example.analysisreport.exception.InvalidRequestException;
import com.example.analysisreport.exception.ResourceNotFound;
import com.example.analysisreport.sample.dto.*;
import com.example.analysisreport.sample.entity.SoilSample;
import com.example.analysisreport.sample.mapper.SampleMapper;
import com.example.analysisreport.sample.repository.SampleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SoilSampleService extends AbstractSampleService<SoilSample, SoilSampleCreateDto, SoilSampleUpdateDto, SoilSampleResponseDto> {

    public SoilSampleService(SampleRepository sampleRepository, SampleMapper sampleMapper, SampleValidationService sampleValidationService, ValidationService validationService) {
        super(sampleRepository, sampleMapper, sampleValidationService, validationService);
    }

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
    @Override
    @Transactional // ensures that the operation is atomic
    public SoilSampleResponseDto create(SoilSampleCreateDto dto) {
        Client client = validationService.loadClient(dto.getClientId());
        Contract contract = validationService.loadAndValidateContract(dto.getContractId(), client);
        // map DTO to Entity
        SoilSample soilSampleEntity = sampleMapper.toEntity(dto);
        // persist entity to database
        SoilSample persistedEntity = sampleValidationService.persistSample(soilSampleEntity, dto.getSampleCode(), client, contract);
        // map Entity to Response DTO
        return sampleMapper.toDto(persistedEntity);
    }

    /**
     * Retrieves all SoilSamples from the database and maps them to DTOs.
     *
     * @return List of SoilSampleResponseDto representing all soil samples.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SoilSampleResponseDto> findAll(Pageable pageable) {
        Page<SoilSample> entityPage = sampleRepository.findAllSoilSamples(pageable);
        return entityPage.map(sampleMapper::toDto);
    }

    @Override
    public SoilSampleResponseDto update(Long id, SoilSampleUpdateDto updateDto) {
        SoilSample existingSample = findSoilSampleEntityById(id);

        // handle potential contract change
        if (updateDto.getContractId() != null) {
            Client client = existingSample.getClient();
            Contract newContract = validationService.loadAndValidateContract(updateDto.getContractId(), client);
            existingSample.setContract(newContract);
        }

        // apply other updates from DTO to entity
        sampleMapper.updateEntityFromDto(updateDto, existingSample);

        // save updated entity
        SoilSample updatedSample = sampleRepository.saveAndFlush(existingSample);
        return sampleMapper.toDto(updatedSample);
    }

    /**
     * Retrieves a SoilSample by its ID and maps it to a DTO.
     *
     * @param id the ID of the soil sample to retrieve
     * @return SoilSampleResponseDto representing the soil sample
     * @throws ResourceNotFound if the soil sample with the given ID does not exist
     */
    @Override
    @Transactional
    public SoilSampleResponseDto findById(Long id) {
        SoilSample soilSample = findSoilSampleEntityById(id);
        return sampleMapper.toDto(soilSample);
    }

    @Override
    protected String getResourceName() {
        return "SoilSample";
    }

    /**
     * Helper method to find a SoilSample entity by its ID or throw an exception if not found.
     *
     * @param id the ID of the soil sample to find
     * @return the found SoilSample entity
     * @throws ResourceNotFound if the soil sample with the given ID does not exist
     */
    private SoilSample findSoilSampleEntityById(Long id) {
        return sampleRepository.findSoilSampleById(id)
                .orElseThrow(() -> new ResourceNotFound(getResourceName() + " not found with id " + id));
    }

}
