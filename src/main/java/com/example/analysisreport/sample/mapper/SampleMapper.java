package com.example.analysisreport.sample.mapper;

import com.example.analysisreport.matrix.entity.SampleMatrix;
import com.example.analysisreport.sample.dto.*;
import com.example.analysisreport.sample.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SampleMapper {

    // ========== toDto (Entity -> DTO) ==========
    // Responsible for creating a flattened representation for the client, by extracting id and name.

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "type", target = "waterType")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(source = "matrix.id", target = "matrixId")
    @Mapping(source = "matrix.name", target = "matrixName")
    WaterSampleResponseDto toDto(WaterSample entity);

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "samplingDepthCentimeters", target = "sampleDepthCm")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(source = "matrix.id", target = "matrixId")
    @Mapping(source = "matrix.name", target = "matrixName")
    SoilSampleResponseDto toDto(SoilSample entity);


    // ========== toEntity (DTO -> Entity) ==========
    // Responsible for translating a creation request into a new entity.

    //    @Mapping(target = "id", ignore = true)
    //    @Mapping(target = "createdAt", ignore = true)
    //    @Mapping(target = "updatedAt", ignore = true)
    //    @Mapping(target = "client", ignore = true) // The service will handle setting this.
    //    @Mapping(target = "contract", ignore = true)
    //    // will be set manually in the service
    //    @Mapping(source = "dto.waterType", target = "type")
    //        // the service sets this
    //    WaterSample toEntity(WaterSampleCreateDto dto, SampleMatrix sampleMatrix);

    /**
     * Custom mapping method to convert WaterSampleCreateDto to WaterSample entity.
     * Caller responsibilities:
     * - The service must validate and resolve dto.getMatrixId() to a non-null SampleMatrix and pass it as sampleMatrix.
     * - The service must validate and set `client` and optionally `contract` on the returned entity before persisting.
     * <p>
     * This mapper intentionally does NOT perform defensive null checks; validation belongs in the service layer.
     *
     * @param dto          The DTO containing data for creating a WaterSample.
     * @param sampleMatrix The resolved SampleMatrix entity associated with the WaterSample (must not be null).
     * @return A new WaterSample entity populated with data from the DTO and associated SampleMatrix.
     */
    default WaterSample toEntity(WaterSampleCreateDto dto, SampleMatrix sampleMatrix) {
        // the service will handle null checks and setting client and contract
        // createdAt and updatedAt are auto-generated
        WaterSample entity = new WaterSample(dto.getSampleCode(), sampleMatrix);
        entity.setSamplingDateTime(dto.getSamplingDateTime());
        entity.setReceivingDateTime(dto.getReceivingDateTime());
        entity.setSampleLocationDetails(dto.getSampleLocationDetails());
        entity.setType(dto.getWaterType());

        return entity;
    }

    /**
     * Custom mapping method to convert SoilSampleCreateDto to SoilSample entity.
     * This method uses the constructor for inherited fields (sampleCode, matrix) and then
     * sets SoilSample-specific fields to build a complete entity.
     * Note: The Lombok @Builder on SoilSample does not include inherited fields from Sample,
     * so we use the constructor to initialize parent fields properly.
     *
     * @param dto          The DTO containing data for creating a SoilSample.
     * @param sampleMatrix The SampleMatrix entity associated with the SoilSample.
     * @return A new SoilSample entity populated with data from the DTO and associated SampleMatrix.
     */
    default SoilSample toEntity(SoilSampleCreateDto dto, SampleMatrix sampleMatrix) {
        SoilSample entity = new SoilSample(dto.getSampleCode(), sampleMatrix);
        entity.setSamplingDateTime(dto.getSamplingDateTime());
        entity.setReceivingDateTime(dto.getReceivingDateTime());
        entity.setSampleLocationDetails(dto.getSampleLocationDetails());
        entity.setSamplingDepthCentimeters(dto.getSampleDepthCm());
        entity.setSoilTexture(dto.getSoilTexture());
        entity.setColor(dto.getColor());
        entity.setLandUse(dto.getLandUse());
        return entity;
    }


    // ========== for Updates (DTO -> Existing Entity) ==========
    // Methods for PATCH/PUT operations.

    /**
     * Updates an existing WaterSample entity with values from a WaterSampleUpdateDto.
     * Only non-null fields in the DTO will be used to update the entity.
     *
     * @param dto    The DTO containing updated values.
     * @param entity The existing WaterSample entity to be updated.
     */
    @Mapping(source = "waterType", target = "type")
    @Mapping(target = "contract", ignore = true)
    @Mapping(target = "createdAt", ignore = true)   // generated and should not be updated
    @Mapping(target = "updatedAt", ignore = true)
    // let @PreUpdate handle this
    @Mapping(target = "client", ignore = true) // client should not be updated
    @Mapping(target = "sampleCode", ignore = true) // sample code should not be updated
    @Mapping(target = "matrix", ignore = true) // matrix should not be updated
    @Mapping(target = "id", ignore = true)
    // telling MapStruct to ignore the id field, as it should not be updated
    // the id is identified as null in save() and thus tries to create a new entity instead of updating the existing one
    void updateEntityFromDto(WaterSampleUpdateDto dto, @MappingTarget WaterSample entity);

    @Mapping(source = "sampleDepthCm", target = "samplingDepthCentimeters")
    @Mapping(target = "contract", ignore = true)
    @Mapping(target = "createdAt", ignore = true)   // generated and should not be updated
    @Mapping(target = "updatedAt", ignore = true)  // let @PreUpdate handle this
    @Mapping(target = "client", ignore = true) // client should not be updated
    @Mapping(target = "sampleCode", ignore = true) // sample code should not be updated
    @Mapping(target = "matrix", ignore = true) // matrix should not be updated
    @Mapping(target = "id", ignore = true)
        // telling MapStruct to ignore the id field, as it should not be updated
        // the id is identified as null in save() and thus tries to create a new entity instead of updating the
        // existing one
    void updateEntityFromDto(SoilSampleUpdateDto dto, @MappingTarget SoilSample entity);

}