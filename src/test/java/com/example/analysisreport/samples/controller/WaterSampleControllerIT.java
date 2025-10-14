package com.example.analysisreport.samples.controller;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.client.repository.ClientRepository;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.contract.entity.ContractType;
import com.example.analysisreport.contract.repository.ContractRepository;
import com.example.analysisreport.matrix.entity.SampleMatrix;
import com.example.analysisreport.matrix.repository.SampleMatrixRepository;
import com.example.analysisreport.samples.dto.WaterSampleCreateDto;
import com.example.analysisreport.samples.dto.WaterSampleUpdateDto;
import com.example.analysisreport.samples.entity.WaterSample;
import com.example.analysisreport.samples.entity.WaterType;
import com.example.analysisreport.samples.repository.SampleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the WaterSampleController.
 * This class sets up the Spring application context and configures
 * MockMvc to simulate HTTP requests to the controller endpoints.
 * Each test method runs in a transaction that rolls back after the test,
 * ensuring a clean state for each test.
 * The tests use the "test" profile, which should configure an in-memory database
 * (like H2) for isolated testing.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
// load the full Spring application context (i.e. AnalysisReportApplication.java) but with a mock web environment
@AutoConfigureMockMvc // auto-configure MockMvc instance to simulate HTTP requests
@Transactional  // ensure each test method runs in a transaction that rolls back after the test
@ActiveProfiles("test") // use the "test" profile for this test class
// explicitly tells Spring Boot to use the application-test.properties file
// this profile should configure an in-memory database (like H2) for isolated testing
public class WaterSampleControllerIT {

    @Autowired // inject the MockMvc instance so we can use it to perform HTTP requests in tests
    private MockMvc mockMvc; // MockMvc instance to perform HTTP requests in tests

    @Autowired  // inject the ObjectMapper instance
    private ObjectMapper objectMapper; // a helper to convert Data Transfer Objects to JSON

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SampleMatrixRepository matrixRepository;

    // =========== Tests for POST /api/v2/water-samples ===========
    @Test
    void whenPostValidWaterSample_thenReturns201Created() throws Exception {
        // create and save a test client
        Client savedClient = createAndSaveTestClient("Test Client", "test address");

        // create and save a test contract for the client
        Contract savedContract = createAndSaveTestContract("1366P", LocalDate.now(), ContractType.CONTRACT, savedClient);

        // create and save a test sample matrix
        SampleMatrix savedMatrix = createAndSaveTestMatrix("Test Water");
        System.out.println("Saved Matrix ID: " + savedMatrix.getId());

        WaterSampleCreateDto createDto = new WaterSampleCreateDto();
        createDto.setSampleCode("WS-001-251009");
        createDto.setMatrixId(savedMatrix.getId());
        createDto.setClientId(savedClient.getId());
        createDto.setContractId(savedContract.getId());
        createDto.setSampleLocationDetails("Discharge from wastewater treatment plant");
        LocalDateTime samplingDateTime = LocalDateTime.of(2025, 10, 9, 9, 9); // October 9, 2025, 9:09 AM
        LocalDateTime receivingDateTime = LocalDateTime.of(2025, 10, 9, 10, 22); // October 9, 2025, 10:22 AM
        createDto.setSamplingDateTime(samplingDateTime);
        createDto.setReceivingDateTime(receivingDateTime);
        createDto.setWaterType(WaterType.TREATED_WASTEWATER);

        mockMvc.perform(post("/api/v2/water-samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.sampleCode").value("WS-001-251009"))
                .andExpect(jsonPath("$.matrixId").value(savedMatrix.getId()))
                .andExpect(jsonPath("$.matrixName").value("Test Water"))
                .andExpect(jsonPath("$.clientId").value(savedClient.getId()))
                .andExpect(jsonPath("$.contractId").value(savedContract.getId()))
                .andExpect(jsonPath("$.sampleLocationDetails").value("Discharge from wastewater treatment plant"))
                .andExpect(jsonPath("$.samplingDateTime").value(samplingDateTime.toString()))
                .andExpect(jsonPath("$.receivingDateTime").value(receivingDateTime.toString()))
                .andExpect(jsonPath("$.waterType").value("TREATED_WASTEWATER"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void whenPostWaterSampleWithMissingSampleCode_thenReturns400BadRequest() throws Exception {
        Client savedClient = createAndSaveTestClient("Test Client", "test address");
        SampleMatrix savedMatrix = createAndSaveTestMatrix("Test Water");

        WaterSampleCreateDto invalidDto = new WaterSampleCreateDto();
        invalidDto.setClientId(savedClient.getId());
        invalidDto.setMatrixId(savedMatrix.getId());
        invalidDto.setSamplingDateTime(LocalDateTime.of(2025, 10, 9, 9, 9));
        invalidDto.setReceivingDateTime(LocalDateTime.of(2025, 10, 9, 10, 22));
        invalidDto.setWaterType(WaterType.DRINKING);

        mockMvc.perform(post("/api/v2/water-samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("sampleCode"));
    }

    @Test
    void whenPostWaterSampleWithInvalidDateRange_thenReturns400BadRequest() throws Exception {
        Client client = createAndSaveTestClient("Client", "Address A");
        SampleMatrix matrix = createAndSaveTestMatrix("Test Water");
        WaterSampleCreateDto invalidDto = new WaterSampleCreateDto();
        invalidDto.setSampleCode("WS-002-251009");
        invalidDto.setMatrixId(matrix.getId());
        invalidDto.setClientId(client.getId());
        invalidDto.setSamplingDateTime(LocalDateTime.of(2025, 10, 9, 10, 22));
        invalidDto.setReceivingDateTime(LocalDateTime.of(2025, 10, 9, 9, 9)); // earlier than sampling
        invalidDto.setWaterType(WaterType.SURFACE);

        mockMvc.perform(post("/api/v2/water-samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("receivingDateTime"));
    }

    @Test
    void whenPostWaterSampleWIthNonExistentClientId_thenReturns404NotFound() throws Exception {
        WaterSampleCreateDto createDto = new WaterSampleCreateDto();
        createDto.setSampleCode("WS-003-251009");

        long nonExistentClientId = 999L;
        SampleMatrix savedMatrix = createAndSaveTestMatrix("Test Water");
        createDto.setMatrixId(savedMatrix.getId());
        createDto.setClientId(nonExistentClientId); // non-existent client ID
        createDto.setSamplingDateTime(LocalDateTime.of(2025, 10, 9, 9, 9));
        createDto.setReceivingDateTime(LocalDateTime.of(2025, 10, 9, 10, 22));
        createDto.setWaterType(WaterType.GROUNDWATER);

        mockMvc.perform(post("/api/v2/water-samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Client not found with id " + nonExistentClientId));
    }


    // =========== Tests for GET /api/v2/water-samples/{id} ===========
    @Test
    void givenWaterSampleExists_whenGetWaterSampleById_thenReturns200Ok() throws Exception {
        Client testClient = createAndSaveTestClient("Client A", "Address A");
        SampleMatrix testMatrix = createAndSaveTestMatrix("Test Water");
        WaterSample ws = new WaterSample("WS-004-251009", testMatrix);
        ws.setClient(testClient);
        ws.setSamplingDateTime(LocalDateTime.of(2025, 10, 9, 9, 9));
        ws.setReceivingDateTime(LocalDateTime.of(2025, 10, 9, 10, 22));
        ws.setType(WaterType.DRINKING);

        // save the entity using the repository to get a generated ID
        WaterSample savedSample = sampleRepository.save(ws);
        Long sampleId = savedSample.getId();

        mockMvc.perform(get("/api/v2/water-samples/" + sampleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleId))
                .andExpect(jsonPath("$.sampleCode").value("WS-004-251009"))
                .andExpect(jsonPath("$.matrixName").value("Test Water"))
                .andExpect(jsonPath("$.clientId").value(testClient.getId()))
                .andExpect(jsonPath("$.samplingDateTime").value("2025-10-09T09:09"))
                .andExpect(jsonPath("$.receivingDateTime").value("2025-10-09T10:22"))
                .andExpect(jsonPath("$.waterType").value("DRINKING"));
    }

    @Test
    void whenGetWaterSampleByNonExistentId_thenReturns404NotFound() throws Exception {
        long nonExistentId = 999L;  // non-existent sample ID

        mockMvc.perform(get("/api/v2/water-samples/" + nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }


    // =========== Tests for PATCH /api/v2/water-samples/{id} ===========
    @Test
    void givenWaterSampleExists_whenPatchWaterSample_thenReturn200OkAndIsUpdated() throws Exception {
        Client client = createAndSaveTestClient("Client B", "Address B");
        SampleMatrix matrix = createAndSaveTestMatrix("Test Water");
        WaterSample originalSample = new WaterSample("WS-005-251009", matrix);
        originalSample.setClient(client);
        originalSample.setSampleLocationDetails("Original Location"); // will be updated
        originalSample.setSamplingDateTime(LocalDateTime.of(2025, 10, 9, 9, 9));
        originalSample.setReceivingDateTime(LocalDateTime.of(2025, 10, 9, 10, 22));
        originalSample.setType(WaterType.SURFACE);    // will be updated

        WaterSample savedSample = sampleRepository.save(originalSample);
        Long sampleId = savedSample.getId();

        WaterSampleUpdateDto updateDto = new WaterSampleUpdateDto();
        updateDto.setSampleLocationDetails("Updated Location");
        updateDto.setWaterType(WaterType.GROUNDWATER);

        mockMvc.perform(patch("/api/v2/water-samples/" + sampleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleId))
                .andExpect(jsonPath("$.sampleCode").value("WS-005-251009")) // unchanged
                .andExpect(jsonPath("$.matrixName").value("Test Water")) // unchanged
                .andExpect(jsonPath("$.clientId").value(client.getId())) // unchanged
                .andExpect(jsonPath("$.sampleLocationDetails").value("Updated Location")) // updated
                .andExpect(jsonPath("$.samplingDateTime").value("2025-10-09T09:09")) // unchanged
                .andExpect(jsonPath("$.receivingDateTime").value("2025-10-09T10:22")) // unchanged
                .andExpect(jsonPath("$.waterType").value("GROUNDWATER"));  // updated
    }

    @Test
    void whenPatchWaterSampleWithInvalidDateRange_thenReturns400BadRequest() throws Exception {
        Client client = createAndSaveTestClient("Client C", "Address C");
        SampleMatrix matrix = createAndSaveTestMatrix("Test Water");
        WaterSample originalSample = new WaterSample("WS-006-251009", matrix);
        originalSample.setClient(client);
        originalSample.setSamplingDateTime(LocalDateTime.of(2025, 10, 9, 9, 9));
        originalSample.setReceivingDateTime(LocalDateTime.of(2025, 10, 9, 10, 22));
        originalSample.setType(WaterType.SURFACE);

        WaterSample savedSample = sampleRepository.save(originalSample);
        Long sampleId = savedSample.getId();

        WaterSampleUpdateDto invalidUpdateDto = new WaterSampleUpdateDto();
        invalidUpdateDto.setSamplingDateTime(LocalDateTime.of(2025, 10, 9, 10, 22));
        invalidUpdateDto.setReceivingDateTime(LocalDateTime.of(2025, 10, 9, 9, 9)); // earlier than sampling

        mockMvc.perform(patch("/api/v2/water-samples/" + sampleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("receivingDateTime"));
    }

    @Test
    void whenPatchNonExistentWaterSample_thenReturns404NotFound() throws Exception {
        long nonExistentId = 999L;

        WaterSampleUpdateDto updateDto = new WaterSampleUpdateDto();
        updateDto.setSampleLocationDetails("This update will fail.");

        mockMvc.perform(patch("/api/v2/water-samples/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Water Sample not found with id " + nonExistentId));
    }


    // =========== Tests for DELETE /api/v2/water-samples/{id} ===========
    @Test
    void givenWaterSampleExists_whenDeleteWaterSample_thenReturns204NoContentAndIsDeleted() throws Exception {
        Client client = createAndSaveTestClient("Test Client", "Test Address");
        SampleMatrix matrix = createAndSaveTestMatrix("Test Water");
        WaterSample sampleToDelete = new WaterSample("WS-007-251009", matrix);
        sampleToDelete.setClient(client);
        sampleToDelete.setSamplingDateTime(LocalDateTime.of(2025, 10, 9, 9, 9));
        sampleToDelete.setReceivingDateTime(LocalDateTime.of(2025, 10, 9, 10, 22));

        WaterSample savedSample = sampleRepository.save(sampleToDelete);
        Long sampleId = savedSample.getId();

        mockMvc.perform(delete("/api/v2/water-samples/" + sampleId))
                .andExpect(status().isNoContent());

        boolean sampleExists = sampleRepository.existsById(sampleId);
        assertFalse(sampleExists, "Sample should have been deleted from the database"); // verify the sample no longer exists
    }

    @Test
    void whenDeleteNonExistentWaterSample_thenReturns404NotFound() throws Exception {
        long nonExistentId = 999L;
        mockMvc.perform(delete("/api/v2/water-samples/" + nonExistentId))
                .andExpect(status().isNotFound());
    }

    // =========== Tests for GET /api/v2/water-samples with pagination and sorting ===========
    @Test
    void givenMultipleWaterSamplesExist_whenGetWaterSamples_thenReturnsPagedResponse() throws Exception {
        Client client = createAndSaveTestClient("Test Client", "Test Address");

        // create and save 15 samples
        int numberOfSamples = 15;
        for (int i = 1; i <= numberOfSamples; i++) {
            SampleMatrix matrix = createAndSaveTestMatrix("Matrix " + i);
            WaterSample sample = new WaterSample("WS-00" + i + "-251009", matrix);
            sample.setClient(client);
            sample.setSamplingDateTime(LocalDateTime.of(2025, 10, 9, 9, 9));
            sample.setReceivingDateTime(LocalDateTime.of(2025, 10, 9, 10, 22));
            sample.setType(WaterType.SURFACE);
            sampleRepository.save(sample);
        }

        mockMvc.perform(get("/api/v2/water-samples")
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
    void whenGetWaterSamplesWithSort_thenReturnsSortedResponse() throws Exception {
        Client client = createAndSaveTestClient("Test Client", "Test Address");
        SampleMatrix matrix = createAndSaveTestMatrix("Test Water");
        WaterSample sampleZ = new WaterSample("ZZZ-999", matrix);
        sampleZ.setClient(client);
        sampleZ.setSamplingDateTime(LocalDateTime.of(2025, 10, 9, 9, 9));
        sampleZ.setReceivingDateTime(LocalDateTime.of(2025, 10, 9, 10, 22));
        sampleZ.setType(WaterType.SURFACE);

        WaterSample sampleA = new WaterSample("AAA-111", matrix);
        sampleA.setClient(client);
        sampleA.setSamplingDateTime(LocalDateTime.of(2025, 10, 9, 9, 9));
        sampleA.setReceivingDateTime(LocalDateTime.of(2025, 10, 9, 10, 22));
        sampleA.setType(WaterType.SURFACE);

        sampleRepository.saveAll(List.of(sampleZ, sampleA));

//        Sample sample = sampleRepository.findAll().get(0);
//        System.out.println(sample);
        mockMvc.perform(get("/api/v2/water-samples")
                        // Add the 'sort' parameter: fieldName,direction
                        .param("sort", "sampleCode,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                // Assert that the first element in the sorted list is the one with sampleCode "AAA-111"
                .andExpect(jsonPath("$.content[0].sampleCode").value("AAA-111"))
                // You can also assert the second element for completeness
                .andExpect(jsonPath("$.content[1].sampleCode").value("ZZZ-999"));
    }

    // =========== Helper Methods ===========
    private Client createAndSaveTestClient(String name, String address) {
        Client client = new Client(name, address);
        return clientRepository.save(client);
    }

    private Contract createAndSaveTestContract(String contractCode, LocalDate date, ContractType type, Client client) {
        Contract contract = new Contract(contractCode);
        contract.setContractDate(date);
        contract.setContractType(type);
        contract.setClient(client);
        return contractRepository.save(contract);
    }

    private SampleMatrix createAndSaveTestMatrix(String name) {
        SampleMatrix matrix = new SampleMatrix();
        matrix.setName(name);
        return matrixRepository.save(matrix);
    }
}
