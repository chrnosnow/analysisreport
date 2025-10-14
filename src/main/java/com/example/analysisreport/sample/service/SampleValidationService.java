package com.example.analysisreport.sample.service;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.exception.DuplicateResourceException;
import com.example.analysisreport.sample.entity.Sample;
import com.example.analysisreport.sample.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleValidationService {

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
