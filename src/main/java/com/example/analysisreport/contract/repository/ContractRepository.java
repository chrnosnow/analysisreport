package com.example.analysisreport.contract.repository;

import com.example.analysisreport.contract.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findAllByClientNameContainingIgnoreCase(String clientName);

    boolean existsByContractCodeAndClientId(String contractCode, Long clientId);
}
