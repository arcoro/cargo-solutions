package com.cargo.solutions.infraestructure.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {

    @NotBlank(message = "Page type is required")
    private String pageType;

    @NotBlank(message = "Icon is required")
    private String icon;

    @NotBlank(message = "Path is required")
    private String path;

    @NotBlank(message = "Title is required")
    private String title;

    private List<String> actions;

    private Integer previousMenu;
}
