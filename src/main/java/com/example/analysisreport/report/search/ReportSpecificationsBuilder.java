package com.example.analysisreport.report.search;

import com.example.analysisreport.report.entity.Report;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ReportSpecificationsBuilder {
    private final List<SpecificationSearchCriteria> params;

    public ReportSpecificationsBuilder(){
        params = new ArrayList<>();
    }

    public final ReportSpecificationsBuilder with(String key, String operation, Object value) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation);
        params.add(new SpecificationSearchCriteria(key, op, value));
        return this;
    }

    public Specification<Report> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification<Report> result = new ReportSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new ReportSpecification(params.get(i)));
        }

        return result;
    }

}
