package com.example.analysisreport.client.repository;

import com.example.analysisreport.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT nextval('client_code_seq')", nativeQuery = true)
    Long getNextSeriesId();

    List<Client> findByOrderByNameAsc();

    List<Client> findAllByNameContainingIgnoreCase(String name);

    boolean existsByClientCode(String clientCode);
}
