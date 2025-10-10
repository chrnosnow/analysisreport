package com.example.analysisreport.contract.dto;

import com.example.analysisreport.contract.entity.ContractType;
import lombok.Data;

import java.time.LocalDate;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class ContractUpdateDto {
    // we don't allow updating contractCode and clientId to maintain data integrity

    private LocalDate contractDate;

    private ContractType contractType;
}
