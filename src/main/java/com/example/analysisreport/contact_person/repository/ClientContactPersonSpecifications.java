package com.example.analysisreport.contact_person.repository;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contact_person.entity.ClientContactPerson;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

//this class is used for finding contact persons by client name using JpaSpecifications
public class ClientContactPersonSpecifications {

    public static Specification<ClientContactPerson> hasClientWithName(String clientName) {
        return (root, query, criteriaBuilder) -> {
            //join tables client_contact_persons (root) and clients (represented by field/object client of the root)
            Join<Client, ClientContactPerson> table = root.join("client");

            //lower case of name in the column in order to extend the search possibilities
            Expression<String> path = criteriaBuilder.lower(table.get("name"));

            //find the names in the column "name" like the name given as the input
            return criteriaBuilder.like(path, "%" + clientName.toLowerCase() + "%");
        };
    }
}
