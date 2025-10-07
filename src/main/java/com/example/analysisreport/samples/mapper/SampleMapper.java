package com.example.analysisreport.samples.mapper;

import com.example.analysisreport.samples.dto.*;
import com.example.analysisreport.samples.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SampleMapper {

    // ========== toDto (Entity -> DTO) ==========
    // Responsible for creating a flattened representation for the client, by extracting id and name.

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "type", target = "waterSampleType")
    WaterSampleResponseDto toDto(WaterSample entity);

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "samplingDepthCentimeters", target = "sampleDepthCm")
    SoilSampleResponseDto toDto(SoilSample entity);


    // ========== toEntity (DTO -> Entity) ==========
    // Responsible for translating a creation request into a new entity.

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "client", ignore = true) // The service will handle setting this.
    @Mapping(target = "contract", ignore = true)
    // will be set manually in the service
    @Mapping(source = "waterSampleType", target = "type")
    WaterSample toEntity(WaterSampleCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "client", ignore = true) // The service will handle setting this.
    @Mapping(target = "contract", ignore = true) // will be set manually in the service
    @Mapping(source = "sampleDepthCm", target = "samplingDepthCentimeters")
    SoilSample toEntity(SoilSampleCreateDto dto);

    // ========== for Updates (DTO -> Existing Entity) ==========
    // Methods for PATCH/PUT operations.

    /**
     * Updates an existing WaterSample entity with values from a WaterSampleUpdateDto.
     * Only non-null fields in the DTO will be used to update the entity.
     *
     * @param dto    The DTO containing updated values.
     * @param entity The existing WaterSample entity to be updated.
     */

    @Mapping(source = "waterSampleType", target = "type")
    @Mapping(target = "contract", ignore = true)
    // contract changes are implemented in the service layer
    void updateEntityFromDto(WaterSampleUpdateDto dto, @MappingTarget WaterSample entity);

    @Mapping(source = "sampleDepthCm", target = "samplingDepthCentimeters")
    @Mapping(target = "contract", ignore = true)
        // contract changes are implemented in the service layer
    void updateEntityFromDto(SoilSampleUpdateDto dto, @MappingTarget SoilSample entity);

}