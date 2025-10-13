package com.example.analysisreport.matrix.mapper;


import com.example.analysisreport.matrix.dto.SampleMatrixCreateDto;
import com.example.analysisreport.matrix.dto.SampleMatrixResponseDto;
import com.example.analysisreport.matrix.dto.SampleMatrixUpdateDto;
import com.example.analysisreport.matrix.entity.SampleMatrix;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SampleMatrixMapper {
    // ========== toDto (Entity -> DTO) ==========
    // Responsible for creating a flattened representation for the client, by extracting id and name.
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    SampleMatrixResponseDto toDto(SampleMatrix entity);

    // ========== toEntity (DTO -> Entity) ==========
    // Responsible for translating a creation request into a new entity.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SampleMatrix toEntity(SampleMatrixCreateDto dto);

    // ========== for Updates (DTO -> Existing Entity) ==========
    // Methods for PATCH/PUT operations.

    /**
     * Updates an existing SampleMatrix entity with values from a SampleMatrixUpdateDto.
     * Only non-null fields in the DTO will be used to update the entity.
     *
     * @param dto    The DTO containing updated values.
     * @param entity The existing SampleMatrix entity to be updated.
     */
    @Mapping(target = "createdAt", ignore = true)   // generated and should not be updated
    @Mapping(target = "updatedAt", ignore = true)
    // let @PreUpdate handle this
    void updateEntityFromDto(SampleMatrixUpdateDto dto, @MappingTarget SampleMatrix entity);
}
