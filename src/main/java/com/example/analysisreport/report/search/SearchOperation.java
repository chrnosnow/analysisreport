package com.example.analysisreport.report.search;

public enum SearchOperation {
    GREATER_THAN_EQUAL, LESS_THAN_EQUAL, EQUAL;

    public static final String[] SIMPLE_OPERATION_SET = {">", "<", ":"};

    public static SearchOperation getSimpleOperation(final String input) {
        switch (input) {
            case ">":
                return GREATER_THAN_EQUAL;
            case "<":
                return LESS_THAN_EQUAL;
            case ":":
                return EQUAL;

            default:
                return null;
        }
    }
}
