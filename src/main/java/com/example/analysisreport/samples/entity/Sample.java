package com.example.analysisreport.samples.entity;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "samples",
        indexes = {
                @Index(name = "idx_client_id", columnList = "client_id"),
                @Index(name = "idx_contract_id", columnList = "contract_id"),
                @Index(name = "idx_samples_sampling_date", columnList = "sampling_date"),
                @Index(name = "idx_samples_receiving_date", columnList = "receiving_date")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_sample_code", columnNames = "sample_code")
        }
)
// joined strategy for inheritance because it normalizes the database structure by storing common attributes in a base
// table and specific attributes in derived tables, reducing data redundancy and improving data integrity.
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor
public abstract class Sample extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sample_code", nullable = false, unique = true)
    private String sampleCode;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    // foreign key column; nullable=false ensures in DB that every sample must be associated with a client
    private Client client;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Setter
    @Column(name = "location_details", length = 500)
    private String sampleLocationDetails;

    @Setter
    @Column(name = "sampling_date", nullable = false)
    private LocalDateTime samplingDateTime;

    @Setter
    @Column(name = "receiving_date", nullable = false)
    private LocalDateTime receivingDateTime;


    // constructor for all mandatory fields required to construct a valid entity for the first time
    // used by the mapper to create a new Sample with its immutable fields
    public Sample(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    @Override
    public String toString() {
        return "id: " + id + ", code: " + sampleCode;
    }
}
