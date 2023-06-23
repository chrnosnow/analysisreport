package com.example.analysisreport.contact_person.repository;

import com.example.analysisreport.contact_person.entity.ClientContactPerson;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientContactPersonRepository extends CrudRepository<ClientContactPerson, Long>, JpaSpecificationExecutor<ClientContactPerson> {

    List<ClientContactPerson> findAll();
    @Query(nativeQuery = true, value = "SELECT cp.* FROM client_contact_persons cp INNER JOIN clients c ON cp.client_id = c.id WHERE lower(c.name) like %:clientName%")
    List<ClientContactPerson> findAllByClientNameContaining(@Param("clientName") String clientName);
}
