package com.example.analysisreport.samples.controller;

import com.example.analysisreport.matrix.dto.SampleMatrixCreateDto;
import com.example.analysisreport.matrix.dto.SampleMatrixUpdateDto;
import com.example.analysisreport.matrix.entity.SampleMatrix;
import com.example.analysisreport.matrix.repository.SampleMatrixRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
// load the full Spring application context (i.e. AnalysisReportApplication.java) but with a mock web environment
@AutoConfigureMockMvc // auto-configure MockMvc instance to simulate HTTP requests
@Transactional  // ensure each test method runs in a transaction that rolls back after the test
@ActiveProfiles("test") // use the "test" profile for this test class
// explicitly tells Spring Boot to use the application-test.properties file
// this profile should configure an in-memory database (like H2) for isolated testing
public class SampleMatrixControllerIT {

    @Autowired // inject the MockMvc instance so we can use it to perform HTTP requests in tests
    private MockMvc mockMvc; // MockMvc instance to perform HTTP requests in tests

    @Autowired  // inject the ObjectMapper instance
    private ObjectMapper objectMapper; // a helper to convert Data Transfer Objects to JSON

    @Autowired
    private SampleMatrixRepository sampleMatrixRepository;


    // =========== Tests for POST /api/v2/matrices ===========
    @Test
    void whenPostValidMatrix_thenReturns201Created() throws Exception {
        // create a test sample matrix using a POST request
        SampleMatrixCreateDto createDto = new SampleMatrixCreateDto();
        createDto.setName("Test Matrix");

        mockMvc.perform(post("/api/v2/matrices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Matrix"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void whenPostMatrixWithMissingName_thenReturns400BadRequest() throws Exception {
        // create a test sample matrix with missing name
        SampleMatrixCreateDto createDto = new SampleMatrixCreateDto();
        createDto.setName(null); // name is required

        mockMvc.perform(post("/api/v2/matrices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Validation failed for one or more fields"))
                .andExpect(jsonPath("$.errors[0].field").value("name"))
                .andExpect(jsonPath("$.errors.length()").value(1));
    }

    @Test
    void whenPostSampleMatrixWithDuplicateName_thenReturns409Conflict() throws Exception {
        // First, create a sample matrix with a specific name
        SampleMatrixCreateDto createDto1 = new SampleMatrixCreateDto();
        createDto1.setName("Duplicate Matrix");

        mockMvc.perform(post("/api/v2/matrices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto1)))
                .andExpect(status().isCreated());

        // Now, attempt to create another sample matrix with the same name
        SampleMatrixCreateDto createDto2 = new SampleMatrixCreateDto();
        createDto2.setName("Duplicate Matrix"); // same name as before

        mockMvc.perform(post("/api/v2/matrices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto2)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Cannot update matrix name. Another matrix with the name '" + createDto2.getName() + "' already exists."));
    }

    // ========== Tests for PUT /api/v2/matrices/{id} ===========
    @Test
    void givenSampleMatrixExists_whenGetMatrixById_thenReturns200Ok() throws Exception {
        // create a sample matrix to ensure there is one to retrieve
        SampleMatrix savedMatrix = createAndSaveSampleMatrix("Air");

        Long matrixId = savedMatrix.getId();

        // Now, retrieve the sample matrix by its ID
        mockMvc.perform(get("/api/v2/matrices/" + matrixId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(matrixId))
                .andExpect(jsonPath("$.name").value("Air"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void whenGetSampleMatrixByNonExistentId_thenReturns404NotFound() throws Exception {
        long nonExistentId = 999L;  // non-existent sample ID

        mockMvc.perform(get("/api/v2/matrices/" + nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    // =========== Tests for PATCH /api/v2/matrices/{id} ===========
    @Test
    void givenSampleMatrixExists_whenPatchMatrix_thenReturns200Ok() throws Exception {
        // create a sample matrix to ensure there is one to update
        SampleMatrix savedMatrix = createAndSaveSampleMatrix("Soil");

        Long matrixId = savedMatrix.getId();

        // create an update DTO to change the name
        String updatedName = "Updated Water Matrix";
        SampleMatrixUpdateDto updateDto = new SampleMatrixUpdateDto();
        updateDto.setName(updatedName);

        // update the sample matrix using PATCH
        mockMvc.perform(patch("/api/v2/matrices/" + matrixId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(matrixId))
                .andExpect(jsonPath("$.name").value(updatedName));
    }

    @Test
    void whenPatchSampleMatrixWithDuplicateName_thenReturns409Conflict() throws Exception {
        // First, create two sample matrices with different names
        SampleMatrix savedMatrix1 = createAndSaveSampleMatrix("Matrix One");
        SampleMatrix savedMatrix2 = createAndSaveSampleMatrix("Matrix Two");

        String duplicateName = "Matrix One"; // name of the first matrix
        SampleMatrixUpdateDto updateDto = new SampleMatrixUpdateDto();
        updateDto.setName(duplicateName); // attempt to change second matrix's name to the first's name

        // attempt to update the second sample matrix to have the same name as the first
        mockMvc.perform(patch("/api/v2/matrices/" + savedMatrix2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Cannot update matrix name. Another matrix with the name '" + duplicateName + "' already exists."));
    }

    @Test
    void whenPatchNonExistentSampleMatrix_thenReturns404NotFound() throws Exception {
        long nonExistentId = 999L;

        SampleMatrixUpdateDto updateDto = new SampleMatrixUpdateDto();
        updateDto.setName("This update will fail.");

        mockMvc.perform(patch("/api/v2/matrices/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Sample matrix with ID " + nonExistentId + " not found."));
    }

    // =========== Tests for DELETE /api/v2/water-samples/{id} ===========
    @Test
    void givenSampleMatrixExists_whenDeleteMatrix_thenReturns204NoContentAndIsDeleted() throws Exception {
        // create a sample matrix to ensure there is one to delete
        SampleMatrix matrixToDelete = createAndSaveSampleMatrix("Test Matrix");

        Long matrixId = matrixToDelete.getId();

        mockMvc.perform(delete("/api/v2/matrices/" + matrixId))
                .andExpect(status().isNoContent());

        boolean sampleExists = sampleMatrixRepository.existsById(matrixId);
        assertFalse(sampleExists, "Sample should have been deleted from the database"); // verify the sample no longer exists
    }

    @Test
    void whenDeleteNonExistentSampleMatrix_thenReturns404NotFound() throws Exception {
        long nonExistentId = 999L;
        mockMvc.perform(delete("/api/v2/matrices/" + nonExistentId))
                .andExpect(status().isNotFound());
    }

    // =========== Tests for GET /api/v2/matrices with pagination and sorting ===========
    @Test
    void givenMultipleSampleMatrixsExist_whenGetMatrices_thenReturnsPagedResponse() throws Exception {
        // create and save 15 sample matrices to test pagination
        int numberOfSamples = 15;
        for (int i = 1; i <= numberOfSamples; i++) {
            SampleMatrix sample = createAndSaveSampleMatrix("Matrix " + i);
        }

        mockMvc.perform(get("/api/v2/matrices")
                        .param("page", "0")
                        .param("size", "10"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(10)) // first page should have 10 samples
                .andExpect(jsonPath("$.page.totalElements").value(numberOfSamples))
                .andExpect(jsonPath("$.page.totalPages").value(2))
                .andExpect(jsonPath("$.page.number").value(0)) // current page number
                .andExpect(jsonPath("$.page.size").value(10)); // page size
    }

    @Test
    void whenGetSampleMatrixWithSort_thenReturnsSortedResponse() throws Exception {
        SampleMatrix matrixA = createAndSaveSampleMatrix("Matrix A");
        SampleMatrix matrixZ = createAndSaveSampleMatrix("Matrix Z");

        mockMvc.perform(get("/api/v2/matrices")
                        .param("sort", "name,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Matrix Z"))
                .andExpect(jsonPath("$.content[1].name").value("Matrix A"));

    }

    // =========== Helper Methods ===========
    private SampleMatrix createAndSaveSampleMatrix(String name) {
        SampleMatrix sampleMatrix = new SampleMatrix();
        sampleMatrix.setName(name);
        return sampleMatrixRepository.save(sampleMatrix);
    }

}
