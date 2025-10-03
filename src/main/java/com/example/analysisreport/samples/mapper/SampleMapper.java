package com.example.analysisreport.samples.mapper;

import com.example.analysisreport.samples.dto.*;
import com.example.analysisreport.samples.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SampleMapper {

    // ========== toDto (Entity -> DTO) ==========
    // Responsible for creating a flattened representation for the client, by extracting id and name.

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "type", target = "waterSampleType")
    WaterSampleResponseDto toDto(WaterSample entity);


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


    // ========== for Updates (DTO -> Existing Entity) ==========
    // This method is for PATCH/PUT operations.

    // void updateSampleFromDto(SampleUpdateDto dto, @MappingTarget Sample entity);
}