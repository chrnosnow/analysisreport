package com.example.analysisreport.core.service;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.client.repository.ClientRepository;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.contract.repository.ContractRepository;
import com.example.analysisreport.exception.InvalidRequestException;
import com.example.analysisreport.exception.ResourceNotFound;
import com.example.analysisreport.matrix.entity.SampleMatrix;
import com.example.analysisreport.matrix.repository.SampleMatrixRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;
    private final SampleMatrixRepository matrixRepository;

    public ValidationService(ClientRepository clientRepository, ContractRepository contractRepository, SampleMatrixRepository matrixRepository) {
        this.clientRepository = clientRepository;
        this.contractRepository = contractRepository;
        this.matrixRepository = matrixRepository;
    }

    /**
     * Finds a Client by its ID or throws an exception if not found.
     *
     * @param id the ID of the client to fetch
     * @return the Client entity
     * @throws ResourceNotFound if the client with the given ID does not exist
     */
    public Client loadClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Client not found with id " + id));
    }

    /**
     * Finds a Contract by its ID or throws an exception if not found.
     *
     * @param id the ID of the contract to fetch
     * @return the Contract entity
     * @throws ResourceNotFound if the contract with the given ID does not exist
     */
    public Contract loadContract(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Contract not found with id " + id));
    }

    /**
     * Finds a Contract by its ID or throws a ResourceNotFound exception.
     * Also validates that the contract belongs to the specified client.
     *
     * @param contractId the ID of the contract to fetch
     * @param client     the ID of the client to validate against
     * @return the Contract entity if found and valid; null if contractId is null
     * @throws ResourceNotFound        if the contract with the given ID does not exist
     * @throws InvalidRequestException if the contract does not belong to the specified client
     */
    public Contract loadAndValidateContract(Long contractId, Client client) {
        if (contractId == null) {
            return null;
        }
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFound("Contract not found with id " + contractId));

        // check if contract's client matches the provided client
        if (!contract.getClient().getId().equals(client.getId())) {
            throw new InvalidRequestException("The provided contract does not belong to the specified client.");
        }

        return contract;
    }

    public SampleMatrix loadSampleMatrix(Long id) {
        return matrixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Sample Matrix not found with id " + id));
    }

}
