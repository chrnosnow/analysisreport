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
import com.example.analysisreport.samples.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleValidationService {

    protected final ClientRepository clientRepository;
    protected final ContractRepository contractRepository;
    protected final SampleRepository sampleRepository;

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
    protected <T extends Sample> T persistSample(T sample, String sampleCode, Client client, Contract contract) {
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
    protected Client loadClient(Long id) {
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
    protected Contract loadAndValidateContract(Long contractId, Client client) {
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
    protected void validateUniqueSampleCode(String sampleCode) {
        if (sampleRepository.existsBySampleCode(sampleCode)) {
            throw new DuplicateResourceException("Sample code must be unique. A" +
                    " sample with code " + sampleCode + " already " +
                    "exists.");
        }
    }
}
