package com.cargo.solutions.domain.ports.out;

import com.cargo.solutions.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findById(Integer roleId);

    Role save(Role role);

    int update(Role role);

    void deleteById(Integer roleId);

    List<Role> findAll();

    String getRoleByUser(String username);
}
