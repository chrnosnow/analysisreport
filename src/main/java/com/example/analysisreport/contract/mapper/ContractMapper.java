package com.example.analysisreport.contract.mapper;

import com.example.analysisreport.contract.dto.ContractCreateDto;
import com.example.analysisreport.contract.dto.ContractResponseDto;
import com.example.analysisreport.contract.dto.ContractUpdateDto;
import com.example.analysisreport.contract.entity.Contract;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContractMapper {

    // ========== toDto (Entity -> DTO) ==========
    // Responsible for creating a flattened representation for the client, by extracting id and name.
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ContractResponseDto toDto(Contract entity);

    // ========== toEntity (DTO -> Entity) ==========
    // Responsible for translating a creation request into a new entity.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "client", ignore = true)
    // client will be set manually in the service
    @Named("createContractFromDto")
    // Give the method a unique name for MapStruct
    // This tells MapStruct that this method is special and should not be used for automatic inference (i.e. influencing the behavior by the mapping from update)
    Contract toEntity(ContractCreateDto dto);

    // ========== for Updates (DTO -> Existing Entity) ==========
    // Methods for PATCH/PUT operations.

    /**
     * Updates an existing Contract entity with values from a ContractUpdateDto.
     * Only non-null fields in the DTO will be used to update the entity.
     *
     * @param dto    The DTO containing updated values.
     * @param entity The existing Contract entity to be updated.
     */
    @Mapping(target = "createdAt", ignore = true)   // generated and should not be updated
    @Mapping(target = "updatedAt", ignore = true)
    // let @PreUpdate handle this
    @Mapping(target = "client", ignore = true) // client should not be updated
    @Mapping(target = "id", ignore = true)
    // telling MapStruct to ignore the id field, as it should not be updated
    // the id is identified as null in save() and thus tries to create a new entity instead of updating the existing one
    @Mapping(target = "contractCode", ignore = true)
    // contract code should not be updated
    void updateEntityFromDto(ContractUpdateDto dto, @MappingTarget Contract entity);
}
