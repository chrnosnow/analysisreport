package com.example.analysisreport.core.service;

import com.example.analysisreport.exception.ResourceNotFound;
import com.example.analysisreport.samples.entity.Sample;
import com.example.analysisreport.samples.mapper.SampleMapper;
import com.example.analysisreport.samples.repository.SampleRepository;
import com.example.analysisreport.samples.service.SampleValidationService;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractSampleService<T extends Sample, C, U, R> implements BaseCrudService<T, Long, C, U, R> {

    protected final SampleRepository sampleRepository;
    protected final SampleMapper sampleMapper;
    protected final SampleValidationService validationService;

    protected AbstractSampleService(SampleRepository sampleRepo, SampleMapper samplemapper, SampleValidationService sampleValidationService) {
        this.sampleRepository = sampleRepo;
        this.sampleMapper = samplemapper;
        this.validationService = sampleValidationService;
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
