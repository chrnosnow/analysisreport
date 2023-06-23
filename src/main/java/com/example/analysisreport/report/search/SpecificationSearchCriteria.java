package com.example.analysisreport.report.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecificationSearchCriteria {
    private String key;
    private SearchOperation operation;
    private Object value;

    public SpecificationSearchCriteria() {}

    public SpecificationSearchCriteria(final String key, final SearchOperation operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
