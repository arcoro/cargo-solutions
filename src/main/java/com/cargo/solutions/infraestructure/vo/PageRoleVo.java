package com.cargo.solutions.infraestructure.vo;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRoleVo {

    @NotNull(message = "pageId is required")
    private Integer pageId;

    @NotNull(message = "roleId is required")
    private Integer roleId;

    private List<String> actions;

}
