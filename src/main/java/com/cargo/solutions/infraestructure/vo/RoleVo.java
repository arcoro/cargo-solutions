package com.cargo.solutions.infraestructure.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {

    @NotBlank(message = "Role name is required")
    private String role;
}
