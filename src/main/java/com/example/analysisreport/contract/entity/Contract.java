package com.example.analysisreport.contract.entity;

import com.example.analysisreport.client.entity.Client;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(
        name = "contracts",
        indexes = {
                @Index(name = "idx_client_id_ctr", columnList = "client_id"),
                @Index(name = "idx_contract_code", columnList = "contract_code")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_contract_code_date", columnNames = {"contract_code", "contract_date"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "contract_code", nullable = false)
    private String contractCode;

    @Column(name = "contract_date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id", updatable = false)
    private Client client;

    @Column(name = "type")
    private ContractType type;

    public Contract(String number, Date date, ContractType type) {
        this.contractCode = number;
        this.date = date;
        this.type = type;
    }
}
