package com.cargo.solutions.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User implements Serializable {

    private UUID id;
    private UUID companyId;
    private Integer roleId;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private Long identityTypeId;
    private String identity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
