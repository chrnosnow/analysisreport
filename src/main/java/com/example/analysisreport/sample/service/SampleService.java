package com.example.analysisreport.sample.service;

import com.example.analysisreport.sample.dto.SampleSummaryDto;
import com.example.analysisreport.sample.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    /**
     * Retrieves all sample summaries from the database.
     * Ideal for displaying a list of samples with minimal details.
     *
     * @return List of SampleSummaryDto representing summaries of all samples.
     */
    @Transactional(readOnly = true)
    // ensures the operation is read-only and can be optimized by the persistence provider
    public List<SampleSummaryDto> getAllSampleSummaries() {
        return sampleRepository.findAllSummaries();
    }

    // to-do maybe
    // public GenericSampleResponseDto findAnySampleById(Long id) { ... }
}
