package com.cargo.solutions.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private UUID id;
    private String companyName;
    private String identifier;
    private String phone;
    private String email;
    private String highStreet;
    private String sideStreet;
    private Long identityTypeId;
    private String identity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
