package com.example.analysisreport.matrix.service;

import com.example.analysisreport.core.service.AbstractCrudService;
import com.example.analysisreport.exception.DuplicateResourceException;
import com.example.analysisreport.exception.ResourceNotFound;
import com.example.analysisreport.matrix.dto.SampleMatrixCreateDto;
import com.example.analysisreport.matrix.dto.SampleMatrixResponseDto;
import com.example.analysisreport.matrix.dto.SampleMatrixUpdateDto;
import com.example.analysisreport.matrix.entity.SampleMatrix;
import com.example.analysisreport.matrix.mapper.SampleMatrixMapper;
import com.example.analysisreport.matrix.repository.SampleMatrixRepository;
import com.example.analysisreport.samples.dto.WaterSampleResponseDto;
import com.example.analysisreport.samples.entity.WaterSample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SampleMatrixService extends AbstractCrudService<SampleMatrix, Long, SampleMatrixCreateDto, SampleMatrixUpdateDto, SampleMatrixResponseDto> {

    private final SampleMatrixRepository sampleMatrixRepository;
    private final SampleMatrixMapper sampleMatrixMapper;


    public SampleMatrixService(SampleMatrixRepository sampleMatrixRepository, SampleMatrixMapper sampleMatrixMapper) {
        super(sampleMatrixRepository);
        this.sampleMatrixRepository = sampleMatrixRepository;
        this.sampleMatrixMapper = sampleMatrixMapper;
    }


    @Override
    protected SampleMatrixResponseDto mapToResponseDto(SampleMatrix entity) {
        return sampleMatrixMapper.toDto(entity);
    }

    @Override
    protected String getResourceName() {
        return "Sample matrix";
    }

    @Override
    @Transactional
    public SampleMatrixResponseDto create(SampleMatrixCreateDto createDto) {
        // check for duplicate name (case-insensitive)
        if (sampleMatrixRepository.existsByNameIgnoreCase(createDto.getName())) {
            throw new DuplicateResourceException("Cannot update matrix name. Another matrix with the name '" + createDto.getName() + "' already exists.");
        }

        // set sample matrix entity from DTO
        SampleMatrix sampleMatrixEntity = sampleMatrixMapper.toEntity(createDto);
        // persist the contract
        SampleMatrix persistedEntity = sampleMatrixRepository.save(sampleMatrixEntity);
        // map Entity to Response DTO
        return mapToResponseDto(persistedEntity);
    }

    @Override
    @Transactional
    public SampleMatrixResponseDto update(Long id, SampleMatrixUpdateDto updateDto) {
        // get the new name from the DTO
        String newName = updateDto.getName();
        // load the existing entity to be updated
        SampleMatrix existingEntity = sampleMatrixRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(getResourceName() + " with ID " + id + " not found."));
        // check if the name is actually being changed
        // if new name is different from the existing name (case-insensitive)
        if (!newName.equalsIgnoreCase(existingEntity.getName())) {
            // check if new name is already taken by another matrix
            sampleMatrixRepository.findByNameIgnoreCaseAndIdNot(newName, id)
                    .ifPresent(matrix -> {
                        throw new DuplicateResourceException("Cannot update matrix name. Another matrix with the name '" + newName + "' already exists.");
                    });
            existingEntity.setName(newName);
        }
        // apply updates from DTO to existing entity
        sampleMatrixMapper.updateEntityFromDto(updateDto, existingEntity);
        // persist the updated entity
        SampleMatrix updatedEntity = sampleMatrixRepository.saveAndFlush(existingEntity);
        // map Entity to Response DTO
        return mapToResponseDto(updatedEntity);
    }


}
