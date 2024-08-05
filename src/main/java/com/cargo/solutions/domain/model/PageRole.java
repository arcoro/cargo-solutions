package com.cargo.solutions.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRole {

    private Integer id;
    private Integer pageId;
    private Integer roleId;
    private List<String> actions;

}
