package com.cargo.solutions.domain.dto;

import com.cargo.solutions.domain.model.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageAction {

    private Page page;
    private List<String> actions;

}
