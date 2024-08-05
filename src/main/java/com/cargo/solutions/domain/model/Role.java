package com.cargo.solutions.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Integer id;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
