package com.cargo.solutions.infraestructure.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Column(name = "role_id", nullable = false)
    private int roleId;

    @Column(name = "name", nullable = false, unique = true, length = 30)
    private String name;

    @Column(name = "last_name", nullable = false, unique = true, length = 30)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @Column(name = "identity_type_id")
    private long identityTypeId;

    @Column(name = "identity", nullable = false, unique = true, length = 15)
    private String identity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
