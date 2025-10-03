package com.example.analysisreport.samples.service;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.client.repository.ClientRepository;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.contract.repository.ContractRepository;
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
public class SampleService {

    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;
    private final SampleMapper sampleMapper;
    private final SampleRepository sampleRepository;

    /**
     * Retrieves all sample summaries from the database.
     *
     * @return List of SampleSummaryDto representing summaries of all samples.
     */
    public List<SampleSummaryDto> getAllSampleSummaries() {
        return sampleRepository.findAllSummaries();
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
    @Transactional // ensures that the operation is atomic
    public WaterSampleResponseDto createWaterSample(WaterSampleCreateDto dto) {
        Client client = loadClient(dto.getClientId());
        Contract contract = loadAndValidateContract(dto.getContractId(), client);
        // map DTO to Entity
        WaterSample waterSampleEntity = sampleMapper.toEntity(dto);
        // persist the sample
        WaterSample persistedEntity = persistSample(waterSampleEntity, dto.getSampleCode(), client, contract);
        // map Entity to Response DTO
        return sampleMapper.toDto(persistedEntity);
    }

    /**
     * Retrieves all WaterSamples from the database and maps them to DTOs.
     *
     * @return List of WaterSampleResponseDto representing all water samples.
     */
    public List<WaterSampleResponseDto> findAllWaterSamples() {
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
    public WaterSampleResponseDto findWaterSampleById(Long id) {
        Optional<WaterSample> optionalWaterSample = sampleRepository.findWaterSampleById(id);
        return optionalWaterSample.map(sampleMapper::toDto) // If the optional has a value, map it to a DTO.
                .orElseThrow(() -> new ResourceNotFound("Water Sample not found with id: " + id)); // If not, throw an exception.
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
    @Transactional
    public SoilSampleResponseDto createSoilSample(@Valid SoilSampleCreateDto dto) {
        Client client = loadClient(dto.getClientId());
        Contract contract = loadAndValidateContract(dto.getContractId(), client);
        // map DTO to Entity
        SoilSample soilSampleEntity = sampleMapper.toEntity(dto);
        // persist entity to database
        SoilSample persistedEntity = persistSample(soilSampleEntity, dto.getSampleCode(), client, contract);
        // map Entity to Response DTO
        return sampleMapper.toDto(persistedEntity);
    }

    /**
     * Retrieves all SoilSamples from the database and maps them to DTOs.
     *
     * @return List of SoilSampleResponseDto representing all soil samples.
     */
    public List<SoilSampleResponseDto> findAllSoilSamples() {
        List<SoilSample> samples = sampleRepository.findAllSoilSamples();
        return samples.stream()
                .map(sampleMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a SoilSample by its ID and maps it to a DTO.
     *
     * @param id the ID of the soil sample to retrieve
     * @return SoilSampleResponseDto representing the soil sample
     * @throws ResourceNotFound if the soil sample with the given ID does not exist
     */
    public SoilSampleResponseDto findSoilSampleById(Long id) {
        Optional<SoilSample> optionalSoilSample = sampleRepository.findSoilSampleById(id);
        return optionalSoilSample.map(sampleMapper::toDto) // If the optional has a value, map it to a DTO.
                .orElseThrow(() -> new ResourceNotFound("Soil Sample not found with id: " + id)); // If not, throw an exception.
    }

    /**
     * Helper method to persist a Sample entity after validating uniqueness of the sample code
     * and setting its associations with Client and Contract.
     *
     * @param sample     the Sample entity to persist
     * @param sampleCode the unique sample code to validate
     * @param client     the associated Client entity
     * @param contract   the associated Contract entity (can be null)
     * @param <T>        the type of Sample (e.g., WaterSample, SoilSample)
     * @return the persisted Sample entity
     * @throws DuplicateResourceException if a sample with the given code already exists
     */
    private <T extends Sample> T persistSample(T sample, String sampleCode, Client client, Contract contract) {
        // check for unique sample code
        validateUniqueSampleCode(sampleCode);
        // set associations
        sample.setClient(client);
        sample.setContract(contract);
        // save the entity to the database and return the persisted entity
        return sampleRepository.save(sample);
    }

    /**
     * Helper method to fetch a Client by ID or throw an exception if not found.
     *
     * @param id the ID of the client to fetch
     * @return the Client entity
     * @throws ResourceNotFound if the client with the given ID does not exist
     */
    private Client loadClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Client not found with id: " + id));
    }

    /**
     * Helper method to fetch and validate a Contract by ID and ensure it belongs to the specified Client.
     *
     * @param contractId the ID of the contract to fetch
     * @param client     the ID of the client to validate against
     * @return the Contract entity if found and valid; null if contractId is null
     * @throws ResourceNotFound        if the contract with the given ID does not exist
     * @throws InvalidRequestException if the contract does not belong to the specified client
     */
    private Contract loadAndValidateContract(Long contractId, Client client) {
        if (contractId == null) {
            return null;
        }
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFound("Contract not found with id: " + contractId));

        // check if contract's client matches the provided client
        if (!contract.getClient().getId().equals(client.getId())) {
            throw new InvalidRequestException("The provided contract does not belong to the specified client.");
        }

        return contract;
    }

    /**
     * Helper method to validate that a sample code is unique.
     *
     * @param sampleCode the sample code to validate
     * @throws DuplicateResourceException if a sample with the given code already exists
     */
    private void validateUniqueSampleCode(String sampleCode) {
        if (sampleRepository.existsBySampleCode(sampleCode)) {
            throw new DuplicateResourceException("Sample code must be unique. A" +
                    " sample with code " + sampleCode + " already " +
                    "exists.");
        }
    }
}
