package com.example.analysisreport.contract.entity;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "contracts",
        indexes = {
                @Index(name = "idx_contract_client_id", columnList = "client_id"),
                @Index(name = "idx_contract_code", columnList = "contract_code")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_contract_code_client", columnNames = {"contract_code", "client_id"})
        }
)
@Getter
@NoArgsConstructor
public class Contract extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "contract_code", nullable = false)
    private String contractCode;

    @Setter
    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id", updatable = false)
    private Client client;

    @Setter
    @Column(name = "type")
    private ContractType contractType;

    // constructor for all mandatory fields required to construct a valid entity for the first time
    public Contract(String contractCode) {
        this.contractCode = contractCode;
    }
}
