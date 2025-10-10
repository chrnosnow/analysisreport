package com.example.analysisreport.contract.dto;

import com.example.analysisreport.contract.entity.ContractType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
// equivalent to @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
public class ContractCreateDto {

    @NotNull
    @NotBlank(message = "Contract code is required when creating a contract.")
    private String contractCode;

    private LocalDate contractDate;

    @NotNull(message = "A client ID is required to create a contract.")
    private Long clientId;

    private ContractType contractType;
}
