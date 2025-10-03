package com.example.analysisreport.contract.entity;

import com.example.analysisreport.client.entity.Client;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "CONTRACTS")
@Getter
@Setter
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", updatable = false)
    private Client client;

    @Column(name = "type")
    private ContractType type;

    public Contract() {
    }

    public Contract(String number, Date date, ContractType type) {
        this.code = number;
        this.date = date;
        this.type = type;
    }
}
