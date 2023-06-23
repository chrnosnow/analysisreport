package com.example.analysisreport.contact_person.repository;

import com.example.analysisreport.contact_person.entity.ClientContactPerson;

import java.util.List;

public interface CustomClientContactPersonRepository {
    List<ClientContactPerson> findByClientName(String clientName);
}
