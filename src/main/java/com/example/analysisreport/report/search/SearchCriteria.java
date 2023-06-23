package com.example.analysisreport.report.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {
    private String key; //the field name, e.g., firstName etc.

    private String operation; //the operation, e.g., equality, less than etc.

    private Object value; //the field value, e.g., john

    public SearchCriteria(final String key, final String operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
