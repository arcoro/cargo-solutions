package com.cargo.solutions.domain.ports.in;

import com.cargo.solutions.domain.dto.PageRoleAction;
import com.cargo.solutions.domain.model.PageRole;

import java.util.List;
import java.util.Optional;

public interface PageRoleService {

    Optional<PageRole> getPageRole(Integer pageRoleId);

    List<PageRole> listAllPageRoles();

    PageRole createPageRole(PageRole pageRole);

    PageRole updatePageRole(Integer pageRoleId, PageRole newPageRole);

    void deletePageRole(Integer pageRoleId);

    PageRoleAction getPageRoleAction(String role);
}
