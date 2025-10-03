package com.example.analysisreport.samples.service;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.client.repository.ClientRepository;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.contract.repository.ContractRepository;
import com.example.analysisreport.exception.DuplicateResourceException;
import com.example.analysisreport.exception.InvalidRequestException;
import com.example.analysisreport.exception.ResourceNotFound;
import com.example.analysisreport.samples.dto.WaterSampleCreateDto;
import com.example.analysisreport.samples.dto.WaterSampleResponseDto;
import com.example.analysisreport.samples.entity.Sample;
import com.example.analysisreport.samples.entity.WaterSample;
import com.example.analysisreport.samples.mapper.SampleMapper;
import com.example.analysisreport.samples.repository.SampleRepository;
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

    @Transactional // ensures that the operation is atomic
    public WaterSampleResponseDto createWaterSample(WaterSampleCreateDto dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFound("Client not found with id: " + dto.getClientId()));

        Contract contract = null;
        if (dto.getContractId() != null) {
            contract = contractRepository.findById(dto.getContractId())
                    .orElseThrow(() -> new ResourceNotFound("Contract not found with id: " + dto.getContractId()));

            // check if contract's client matches the provided client
            if (!contract.getClient().getId().equals(client.getId())) {
                throw new InvalidRequestException("The provided contract does not belong to the specified client.");
            }
        }

        // check if sampleCode is unique
        if (sampleRepository.existsBySampleCode(dto.getSampleCode())) {
            throw new DuplicateResourceException("Sample code must be unique. A" +
                    " sample with code " + dto.getSampleCode() + " already " +
                    "exists.");
        }

        // Persistence logic
        // map DTO to Entity
        WaterSample waterSampleEntity = sampleMapper.toEntity(dto);
        waterSampleEntity.setClient(client);
        waterSampleEntity.setContract(contract);

        // save entity to database
        WaterSample persistedEntity = sampleRepository.save(waterSampleEntity);
        // 'persistedEntity' now has its ID and timestamps set by the database

        // map Entity to Response DTO
        return sampleMapper.toDto(persistedEntity);
    }

    public List<WaterSampleResponseDto> findAllWaterSamples() {
        List<WaterSample> samples = sampleRepository.findAllWaterSamples();
        return samples.stream()
                .map(sampleMapper::toDto)
                .toList();
    }

    public WaterSampleResponseDto findWaterSampleById(Long id) {
        Optional<WaterSample> optionalWaterSample = sampleRepository.findWaterSampleById(id);
        return optionalWaterSample.map(sampleMapper::toDto) // If the optional has a value, map it to a DTO.
                .orElseThrow(() -> new ResourceNotFound("Water Sample not found with id: " + id)); // If not, throw an exception.
    }
}
