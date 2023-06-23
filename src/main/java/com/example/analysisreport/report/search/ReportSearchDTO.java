package com.example.analysisreport.report.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReportSearchDTO {

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date startDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date endDate;

    private Long analystId;

    private Long clientId;

    private Long contractId;
}
