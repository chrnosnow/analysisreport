package com.example.analysisreport.contact_person.entity;

import com.example.analysisreport.client.entity.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "CLIENT_CONTACT_PERSONS")
public class ClientContactPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "id", nullable = false, updatable = false)
    private Client client;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    public ClientContactPerson() {
    }

    public ClientContactPerson(Client client, String firstName, String lastName, String position,
                               String email, String phone) {

        this.client = client;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.phone = phone;

    }

    public ClientContactPerson(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
