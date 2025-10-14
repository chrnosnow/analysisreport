package com.example.analysisreport.report.search;

import com.example.analysisreport.report.entity.Report;
import com.example.analysisreport.sample.entity.WaterSample;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ReportSpecification implements Specification<Report> {

    private SpecificationSearchCriteria criteria;

    public ReportSpecification(final SpecificationSearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Join<Report, WaterSample> sampleJoin = root.join("waterSample");

        switch (criteria.getOperation()) {

            case GREATER_THAN_EQUAL:
                if (criteria.getValue() instanceof Date) {
                    return builder.greaterThanOrEqualTo(root.<Date>get(criteria.getKey()),
                            ((Date) criteria.getValue()));
                } else {
                    return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()),
                            criteria.getValue().toString());
                }

            case LESS_THAN_EQUAL:
                if (criteria.getValue() instanceof Date) {
                    return builder.lessThanOrEqualTo(root.<Date>get(criteria.getKey()),
                            (((Date) criteria.getValue())));
                } else {
                    return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()),
                            criteria.getValue().toString());
                }

            case EQUAL:
                if (criteria.getValue() instanceof Date) {
                    return builder.equal(root.<Date>get(criteria.getKey()),
                            ((Date) criteria.getValue()));
                } else if (criteria.getKey().toString().equals("contractId")) {
                    return builder.equal(sampleJoin.<String>get(criteria.getKey()), criteria.getValue());
                } else if (criteria.getKey().toString().equals("clientId")) {
                    return builder.equal(sampleJoin.<String>get(criteria.getKey()), criteria.getValue());
                } else {
                    return builder.equal(root.<String>get(criteria.getKey()), criteria.getValue());
                }

            default:
                return null;
        }
    }
}
