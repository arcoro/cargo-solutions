package com.cargo.solutions.infraestructure.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "companies")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "high_street", nullable = false)
    private String highStreet;

    @Column(name = "side_street", nullable = false)
    private String sideStreet;

    @Column(name = "identity_type_id")
    private Long identityTypeId;

    @Column(name = "identity", nullable = false, unique = true)
    private String identity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
