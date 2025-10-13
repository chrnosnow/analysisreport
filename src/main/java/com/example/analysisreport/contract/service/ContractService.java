package com.example.analysisreport.contract.service;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contract.dto.ContractCreateDto;
import com.example.analysisreport.contract.dto.ContractResponseDto;
import com.example.analysisreport.contract.dto.ContractUpdateDto;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.contract.mapper.ContractMapper;
import com.example.analysisreport.contract.repository.ContractRepository;
import com.example.analysisreport.core.service.AbstractCrudService;
import com.example.analysisreport.core.service.ValidationService;
import com.example.analysisreport.exception.DuplicateResourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractService extends AbstractCrudService<Contract, Long, ContractCreateDto, ContractUpdateDto, ContractResponseDto> {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;
    private final ValidationService validationService;


    public ContractService(ContractRepository contractRepository, ContractMapper contractMapper, ValidationService validationService) {
        super(contractRepository);
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
        this.validationService = validationService;
    }

    @Override
    protected ContractResponseDto mapToResponseDto(Contract entity) {
        return contractMapper.toDto(entity);
    }

    @Override
    protected String getResourceName() {
        return "Contract";
    }

    @Override
    @Transactional
    public ContractResponseDto create(ContractCreateDto createDto) {
        Client client = validationService.loadClient(createDto.getClientId());
        // validate for unique contract code and date
        if (createDto.getContractDate() != null) {
            validateUniqueContractCodeAndClient(createDto.getContractCode(), createDto.getClientId());
        }
        // map DTO to Entity
        Contract contractEntity = contractMapper.toEntity(createDto);
        contractEntity.setClient(client);
        // persist the contract
        Contract persistedEntity = contractRepository.save(contractEntity);
        // map Entity to Response DTO
        return mapToResponseDto(persistedEntity);
    }

    @Override
    @Transactional
    public ContractResponseDto update(Long id, ContractUpdateDto updateDto) {
        Contract existingContract = validationService.loadContract(id);
        // apply updates from DTO to entity
        contractMapper.updateEntityFromDto(updateDto, existingContract);
        // persist the updated contract
        Contract updatedContract = contractRepository.saveAndFlush(existingContract);
        // map Entity to Response DTO
        return mapToResponseDto(updatedContract);
    }

    /**
     * Validates that a contract with the given code and date does not already exist.
     *
     * @param contractCode the contract code to validate
     * @param clientId     the client ID to validate
     * @throws DuplicateResourceException if a contract with the given code and date already exists
     */
    private void validateUniqueContractCodeAndClient(String contractCode, Long clientId) {
        if (contractRepository.existsByContractCodeAndClientId(contractCode, clientId)) {
            throw new DuplicateResourceException("Contract already exists.");
        }
    }
}
