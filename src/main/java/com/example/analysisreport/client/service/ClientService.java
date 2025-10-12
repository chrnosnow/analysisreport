package com.example.analysisreport.client.service;

import com.example.analysisreport.client.dto.ClientCreateDto;
import com.example.analysisreport.client.dto.ClientUpdateDto;
import com.example.analysisreport.client.dto.ClientResponseDto;
import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.client.mapper.ClientMapper;
import com.example.analysisreport.client.repository.ClientRepository;
import com.example.analysisreport.core.service.AbstractCrudService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends AbstractCrudService<Client, Long, ClientCreateDto,
        ClientUpdateDto, ClientResponseDto> {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        super(clientRepository);
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    protected ClientResponseDto mapToResponseDto(Client entity) {
        return clientMapper.toDto(entity);
    }

    @Override
    protected String getResourceName() {
        return "Client";
    }

    @Override
    @Transactional
    public ClientResponseDto create(ClientCreateDto createDto) {
        // generate the unique client code
        Long nextId = clientRepository.getNextSeriesId();
        String clientCode = String.format("CLT-%05d", nextId);

        // map DTO to entity
        Client clientEntity = new Client(clientCode, createDto.getName());
        if (createDto.getAddress() != null) {
            clientEntity.setAddress(createDto.getAddress());
        }
        // persist the client
        Client persistedClient = clientRepository.save(clientEntity);
        // map entity to Response DTO
        return mapToResponseDto(persistedClient);
    }

    @Override
    @Transactional
    public ClientResponseDto update(Long id, ClientUpdateDto updateDto) {
        Client existingClient = findEntityById(id);
        //apply updates from DTO to Entity
        clientMapper.updateEntityFromDto(updateDto, existingClient);
        // persist the updated client
        Client updatedClient = clientRepository.saveAndFlush(existingClient);
        // map entity to response DTO
        return mapToResponseDto(updatedClient);
    }
}
