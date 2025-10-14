package com.example.analysisreport.report.entity;

import com.example.analysisreport.sample.entity.WaterSample;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "REPORTS")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "REPORT_NUMBER", nullable = false)
    private int reportNumber;

    @Column(name = "REPORT_ISSUE_DATE")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date issueDate;

    @ManyToOne
    //a report can have only a part of the quality indicators requested initially or another report for the same
    // sample can be issued due to various errors on the first one, which was already given to the client
    @JoinColumn(referencedColumnName = "id", name = "SAMPLE_ID", nullable = false, updatable = false)
    private WaterSample waterSample;

    @Column(name = "ANALYST_ID")
    private Long analystId;

}
