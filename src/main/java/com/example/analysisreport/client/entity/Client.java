package com.example.analysisreport.client.entity;

import com.example.analysisreport.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(
        name = "clients",
        indexes = {
                @Index(name = "idx_client_name", columnList = "name"),
                @Index(name = "idx_client_code", columnList = "client_code")
        }
)
@NoArgsConstructor
public class Client extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_code", nullable = false, unique = true, updatable = false)
    private String clientCode;

    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Setter
    @Column(name = "address")
    private String address;


    public Client(String clientCode, String name) {
        this.clientCode = clientCode;
        this.name = name;
    }

}
