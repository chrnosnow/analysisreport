package com.example.analysisreport.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


// This class handles exceptions globally for all controllers in the application.
@RestControllerAdvice // Combines @ControllerAdvice and @ResponseBody
// @ControllerAdvice allows you to handle exceptions across the whole application in one global handling component.
// @ResponseBody ensures that the return value of methods is serialized directly to the HTTP response body.
// This is particularly useful for RESTful APIs where you want to return JSON or XML responses.
public class GlobalExceptionHandler {

    /**
     * Handles validation errors for method arguments annotated with @Valid.
     * Triggered when Spring's validation fails on a request body.
     * Returns a map of field names to error messages when validation fails.
     *
     * @param ex the exception thrown when validation fails
     * @return a map containing field names and their corresponding error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Map each validation error to our custom ValidationError record
        List<ValidationError> validationErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return new ValidationError(fieldName, errorMessage);
                })
                .collect(Collectors.toList());

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed for one or more fields", // A generic top-level message
                request.getRequestURI(),
                validationErrors // The structured list of errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ResourceNotFoundException and returns a structured error response.
     * This exception is used when a requested resource cannot be found.
     * For example, when trying to retrieve a sample by an ID that does not exist.
     *
     * @param ex      the exception thrown when a resource is not found
     * @param request the HTTP request that resulted in the exception
     * @return a ResponseEntity containing the ApiErrorResponse and HTTP status code 404
     */
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFound ex, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),   // 404
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles DuplicateResourceException and returns a structured error response.
     * This exception is used when attempting to create a resource that already exists.
     * For example, trying to create a user with an email that is already registered.
     *
     * @param ex      the exception thrown when a duplicate resource is detected
     * @param request the HTTP request that resulted in the exception
     * @return a ResponseEntity containing the ApiErrorResponse and HTTP status code 409
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),    // 409
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


    /**
     * Handles InvalidRequestException and returns a structured error response.
     * This exception is used for general invalid requests that do not fit other categories.
     * For example, when request parameters are invalid or missing.
     *
     * @param ex      the exception thrown when a request is invalid
     * @param request the HTTP request that resulted in the exception
     * @return a ResponseEntity containing the ApiErrorResponse and HTTP status code 400
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidRequest(InvalidRequestException ex, HttpServletRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(), // 400
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
