package com.example.analysisreport.contract.entity;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contract.entity.ContractType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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

    @Column(name = "CONTRACT_NUMBER")
    private String number;

    @Column(name = "CONTRACT_DATE")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "id", updatable = false)
    private Client client;

    @Column(name = "CONTRACT_TYPE")
    private ContractType type;

    public Contract(){}

    public Contract(String number, Date date, ContractType type){
        this.number = number;
        this.date = date;
        this.type = type;
    }
}
