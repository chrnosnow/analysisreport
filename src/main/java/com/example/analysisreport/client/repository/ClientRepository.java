package com.example.analysisreport.client.repository;

import com.example.analysisreport.client.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findAll();
    List<Client> findByOrderByNameAsc();
    List<Client> findAllByNameContainingIgnoreCase(String name);
}
