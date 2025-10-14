package com.example.analysisreport.core.service;

import com.example.analysisreport.exception.ResourceNotFound;
import com.example.analysisreport.sample.entity.Sample;
import com.example.analysisreport.sample.mapper.SampleMapper;
import com.example.analysisreport.sample.repository.SampleRepository;
import com.example.analysisreport.sample.service.SampleValidationService;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractSampleService<T extends Sample, C, U, R> implements BaseCrudService<T, Long, C, U, R> {

    protected final SampleRepository sampleRepository;
    protected final SampleMapper sampleMapper;
    protected final SampleValidationService sampleValidationService;
    protected final ValidationService validationService;

    protected AbstractSampleService(SampleRepository sampleRepo, SampleMapper samplemapper, SampleValidationService sampleValidationService, ValidationService validationService) {
        this.sampleRepository = sampleRepo;
        this.sampleMapper = samplemapper;
        this.sampleValidationService = sampleValidationService;
        this.validationService = validationService;
    }

    /**
     * Deletes a sample by its ID after ensuring it exists.
     * If the sample does not exist, an exception is thrown.
     *
     * @param id the ID of the sample to delete
     * @throws RuntimeException if the sample with the given ID does not exist
     */

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!sampleRepository.existsById(id)) {
            throw new ResourceNotFound(getResourceName() + " not found with id " + id);
        }        // ensure entity exists or throw
        sampleRepository.deleteById(id);
    }

    // Get the resource name for error messages
    protected abstract String getResourceName();
}
