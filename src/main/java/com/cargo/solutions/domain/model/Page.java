package com.cargo.solutions.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    private Integer id;
    private String pageType;
    private String icon;
    private String path;
    private String title;
    private List<String> actions;
    private Integer previousMenu;

}
