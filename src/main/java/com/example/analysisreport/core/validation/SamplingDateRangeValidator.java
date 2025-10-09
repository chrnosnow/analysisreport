package com.example.analysisreport.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

/**
 * Validator to ensure that the receiving date is after or equal to the sampling date.
 * This class must implement the ConstraintValidator interface. The interface takes two generic types:
 * - the annotation it's for (ValidDateRange).
 * - the type of object it can validate: WaterSampleCreateDto.
 * Automatically invoked by the validation framework.
 * Returns true if valid, false otherwise.
 * Usage:
 * Annotate the DTO class with @ValidDateRange to apply this validation.
 */
public class SamplingDateRangeValidator implements ConstraintValidator<ValidDateRange, DateRangeProvider> {
    /**
     * This method contains the validation logic.
     * It checks if the receiving date is after or equal to the sampling date.
     *
     * @param dto     The WaterSampleCreateDto object to validate.
     * @param context The context in which the constraint is evaluated.
     * @return true if the date range is valid, false otherwise.
     */
    @Override
    public boolean isValid(DateRangeProvider dto, ConstraintValidatorContext context) {
        LocalDateTime startDate = dto.getSamplingDateTime();
        LocalDateTime endDate = dto.getReceivingDateTime();
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }

        // if the validation logic fails
        if (endDate.isBefore(startDate)) {
            // disable the default error message (the one on the class, from the @ValidDateRange annotation)
            context.disableDefaultConstraintViolation();

            // build a new, custom error message and attach it to a specific field (and not the entire WaterSampleCreateDto object)
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("receivingDateTime") // attach the error to the receivingDateTime property of the DTO
                    .addConstraintViolation();

            return false; // validation fails
        }
        return true; // validation passed
    }
}
