package com.example.analysisreport.core.validation;

import java.time.LocalDateTime;

/**
 * Interface to provide start and end dates for date range validation.
 * Implemented by DTOs that require date range validation.
 */
public interface DateRangeProvider {
    LocalDateTime getSamplingDateTime();

    LocalDateTime getReceivingDateTime();
}
