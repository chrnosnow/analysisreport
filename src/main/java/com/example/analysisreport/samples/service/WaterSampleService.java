package com.example.analysisreport.samples.service;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.core.service.AbstractSampleService;
import com.example.analysisreport.exception.DuplicateResourceException;
import com.example.analysisreport.exception.InvalidRequestException;
import com.example.analysisreport.exception.ResourceNotFound;
import com.example.analysisreport.samples.dto.*;
import com.example.analysisreport.samples.entity.WaterSample;
import com.example.analysisreport.samples.mapper.SampleMapper;
import com.example.analysisreport.samples.repository.SampleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WaterSampleService extends AbstractSampleService<WaterSample, WaterSampleCreateDto, WaterSampleUpdateDto, WaterSampleResponseDto> {

    public WaterSampleService(SampleRepository sampleRepo, SampleMapper samplemapper, SampleValidationService sampleValidationService) {
        super(sampleRepo, samplemapper, sampleValidationService);
    }

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
    @Override
    @Transactional(readOnly = true)
    public Page<WaterSampleResponseDto> findAll(Pageable pageable) {
        Page<WaterSample> entityPage = sampleRepository.findAllWaterSamples(pageable);
        return entityPage.map(sampleMapper::toDto);
    }

    /**
     * Retrieves a WaterSample by its ID and maps it to a DTO.
     *
     * @param id the ID of the water sample to retrieve
     * @return WaterSampleResponseDto representing the water sample
     * @throws ResourceNotFound if the water sample with the given ID does not exist
     */
    @Override
    @Transactional(readOnly = true)
    public WaterSampleResponseDto findById(Long id) {
        WaterSample waterSample = findWaterSampleEntityById(id);
        return sampleMapper.toDto(waterSample);
    }

    @Override
    @Transactional // ensures that the operation is atomic
    public WaterSampleResponseDto update(Long id, WaterSampleUpdateDto updateDto) {

        WaterSample existingSample = findWaterSampleEntityById(id);

        // handle potential contract change
        if (updateDto.getContractId() != null) {
            Client client = existingSample.getClient();
            Contract newContract = validationService.loadAndValidateContract(updateDto.getContractId(), client);
            existingSample.setContract(newContract);
        }

        // apply updates from DTO to entity
        sampleMapper.updateEntityFromDto(updateDto, existingSample);

        WaterSample updatedSample = sampleRepository.saveAndFlush(existingSample);
        return sampleMapper.toDto(updatedSample);
    }

    @Override
    protected String getResourceName() {
        return "Water Sample";
    }

    /**
     * Helper method to find a WaterSample entity by its ID or throw an exception if not found.
     *
     * @param id the ID of the water sample to find
     * @return the found WaterSample entity
     * @throws ResourceNotFound if the water sample with the given ID does not exist
     */
    private WaterSample findWaterSampleEntityById(Long id) {
        return sampleRepository.findWaterSampleById(id)
                .orElseThrow(() -> new ResourceNotFound(getResourceName() + " not found with id " + id));
    }
}
