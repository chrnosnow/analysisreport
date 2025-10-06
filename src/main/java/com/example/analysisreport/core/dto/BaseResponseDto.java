package com.example.analysisreport.core.dto;

// interface for response DTOs to ensure they have a getId() method
// used in generic services and controllers
// I is the type of the ID, e.g., Long, String, etc.
public interface BaseResponseDto<I> {
    I getId();
}
