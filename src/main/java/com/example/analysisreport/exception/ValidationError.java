package com.example.analysisreport.exception;

/**
 * A record representing a validation error with fields and a message.
 *
 * @param field   the fields that caused the validation error
 * @param message the error message associated with the validation error
 */
public record ValidationError(String field, String message) {
}
