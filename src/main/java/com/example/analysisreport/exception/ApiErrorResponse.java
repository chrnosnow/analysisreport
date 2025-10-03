package com.example.analysisreport.exception;

// an immutable record representing a standardized error response
public record ApiErrorResponse(
        int status,
        String error,
        String message,
        String path) {
}
