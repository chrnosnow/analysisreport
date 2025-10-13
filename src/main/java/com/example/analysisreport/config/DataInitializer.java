package com.example.analysisreport.config;

import com.example.analysisreport.matrix.entity.SampleMatrix;
import com.example.analysisreport.matrix.repository.SampleMatrixRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j // Good for logging that the seeding is happening
public class DataInitializer implements ApplicationRunner {

    private final SampleMatrixRepository matrixRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting data initialization...");

        // Define the list of essential matrices
        List<String> matrixNames = List.of(
                "Water",
                "Soil",
                "Air",
                "Waste"
        );

        // Loop through and create each one only if it doesn't already exist
        for (String name : matrixNames) {
            createMatrixIfNotFound(name);
        }

        log.info("Data initialization complete.");
    }

    private void createMatrixIfNotFound(String name) {
        // This check makes the process idempotent (safe to run multiple times)
        if (!matrixRepository.existsByNameIgnoreCase(name)) {
            SampleMatrix matrix = new SampleMatrix();
            matrix.setName(name);
            matrixRepository.save(matrix);
            log.info("Created matrix: {}", name);
        }
    }
}
