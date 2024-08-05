package com.cargo.solutions.domain.ports.in;

import com.cargo.solutions.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> getRole(Integer roleId);

    List<Role> listAllRoles();

    Role createRole(Role role);

    Role updateRole(Integer roleId, Role newRole);

    void deleteRole(Integer roleId);

    String getRoleByUser(String username);
}
