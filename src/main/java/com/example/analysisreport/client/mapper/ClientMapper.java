package com.example.analysisreport.client.mapper;

import com.example.analysisreport.client.dto.ClientCreateDto;
import com.example.analysisreport.client.dto.ClientResponseDto;
import com.example.analysisreport.client.dto.ClientUpdateDto;
import com.example.analysisreport.client.entity.Client;

import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
// This makes it a Spring bean that you can inject elsewhere.
// Ignore null values with PATCH requests.
public interface ClientMapper {

    // ========== toDto (Entity -> DTO) ==========
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ClientResponseDto toDto(Client entity);

    // ========== toEntity (DTO -> Entity) ==========
    // Responsible for translating a creation request into a new entity.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "clientCode", ignore = true)
    // client code will be set manually in the service
    Client toEntity(ClientCreateDto dto);

    // ========== for Updates (DTO -> Existing Entity) ==========
    // Methods for PATCH/PUT operations.

    /**
     * Updates an existing Client entity with values from a ClientUpdateDto.
     * Only non-null fields in the DTO will be used to update the entity.
     *
     * @param dto    The DTO containing updated values.
     * @param entity The existing Client entity to be updated.
     */
    @Mapping(target = "createdAt", ignore = true)   // generated and should not be updated
    @Mapping(target = "updatedAt", ignore = true)
    // let @PreUpdate handle this
    @Mapping(target = "clientCode", ignore = true) // client code should not be updated
    @Mapping(target = "id", ignore = true)
    // telling MapStruct to ignore the id field, as it should not be updated
    // the id is identified as null in save() and thus tries to create a new entity instead of updating the existing one
    void updateEntityFromDto(ClientUpdateDto dto, @MappingTarget Client entity);
}
