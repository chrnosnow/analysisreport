package com.example.analysisreport.contract.repository;

import com.example.analysisreport.contract.entity.Contract;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContractRepository extends CrudRepository<Contract, Long> {
    public List<Contract> findAll();

    public List<Contract> findAllByClientNameContainingIgnoreCase(String clientName);

    Contract save(Contract contract);
}
