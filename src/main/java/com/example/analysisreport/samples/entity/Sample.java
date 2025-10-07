package com.example.analysisreport.samples.entity;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contract.entity.Contract;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

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
@Setter
@NoArgsConstructor
public abstract class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId // ensures that the sampleCode is unique and can be used as a natural identifier for the entity
    @NotBlank
    @Column(name = "sample_code", nullable = false, unique = true)
    private String sampleCode;

    @NotNull // ensures that the field cannot be null in the entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    // foreign key column; nullable=false ensures in DB that every sample must be associated with a client
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Size(max = 500)
    @Column(name = "location_details", length = 500)
    private String sampleLocationDetails;

    @NotNull
    @PastOrPresent(message = "Sampling date cannot be in the future")
    @Column(name = "sampling_date", nullable = false)
    private LocalDateTime samplingDateTime;

    @NotNull
    @Column(name = "receiving_date", nullable = false)
    private LocalDateTime receivingDateTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // automatically set createdAt and updatedAt before persisting and updating
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Bean validation method to ensure receivingDateTime is after or equal to samplingDateTime
    @AssertTrue(message = "Receiving date must be after or equal to sampling date")
    private boolean isReceivingDateValid() {
        return samplingDateTime == null || receivingDateTime == null || !receivingDateTime.isBefore(samplingDateTime);
    }


    @Override
    public String toString() {
        return "id: " + id + ", code: " + sampleCode;
    }
}
