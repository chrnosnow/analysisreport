package com.example.analysisreport.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * A class-level validation annotation to ensure that the receiving date is after or equal to the sampling date.
 */
@Target({ElementType.TYPE}) // ensure that this annotation is applied at the class/interface/record level
// This is because we need access to multiple fields (samplingDateTime and receivingDateTime) to perform the check.
@Retention(RetentionPolicy.RUNTIME) // make this annotation available at runtime
@Constraint(validatedBy = SamplingDateRangeValidator.class) // specifies the class that contains the validation logic
public @interface ValidDateRange {
    // Default error message if validation fails
    String message() default "End date must be after or equal to start date";

    // Standard boilerplate for validation annotations
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
