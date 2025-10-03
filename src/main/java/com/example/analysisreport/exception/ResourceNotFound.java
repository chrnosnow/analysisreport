package com.example.analysisreport.exception;

//custom exception class to handle resource not found scenarios
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
    }
}
