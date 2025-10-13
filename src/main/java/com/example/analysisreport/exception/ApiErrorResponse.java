package com.example.analysisreport.exception;

import java.util.List;

/**
 * A record representing a standardized API error response.
 *
 * @param status  the HTTP status code of the error
 * @param error   a brief description of the error type
 * @param message a detailed error message
 * @param path    the request path that caused the error
 * @param errors  a list of validation errors, if applicable
 */
public record ApiErrorResponse(
        int status,
        String error,
        String message,
        String path,
        List<ValidationError> errors) {
    public ApiErrorResponse(int status, String error, String message, String path) {
        this(status, error, message, path, null);
    }
}
