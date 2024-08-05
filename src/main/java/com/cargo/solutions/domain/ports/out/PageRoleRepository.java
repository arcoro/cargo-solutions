package com.cargo.solutions.domain.ports.out;

import com.cargo.solutions.domain.dto.PageRoleAction;
import com.cargo.solutions.domain.model.PageRole;

import java.util.List;
import java.util.Optional;

public interface PageRoleRepository {
    Optional<PageRole> findById(Integer pageRoleId);

    PageRole save(PageRole pageRole);

    int update(PageRole pageRole);

    void deleteById(Integer pageRoleId);

    List<PageRole> findAll();

    PageRoleAction findByRole(String role);
}
