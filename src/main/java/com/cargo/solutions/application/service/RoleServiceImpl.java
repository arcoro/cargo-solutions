package com.cargo.solutions.application.service;

import com.cargo.solutions.domain.exception.DataEntityException;
import com.cargo.solutions.domain.model.Role;
import com.cargo.solutions.domain.ports.in.RoleService;
import com.cargo.solutions.domain.ports.out.RoleRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> getRole(Integer roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public List<Role> listAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role role) {
        role.setCreatedAt(LocalDateTime.now());
        return save(role);
    }

    @Override
    public Role updateRole(Integer roleId, Role newRole) {
        newRole.setId(roleId);
        newRole.setUpdatedAt(LocalDateTime.now());
        if (roleRepository.update(newRole) == 0) throw new DataEntityException("Not found Role");
        return newRole;
    }

    @Override
    public void deleteRole(Integer roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public String getRoleByUser(String username) {
        try {
            return roleRepository.getRoleByUser(username);
        } catch (Exception ignore) {
            throw new DataEntityException("Not found Role");
        }
    }

    private Role save(Role role) {
        try {
            return roleRepository.save(role);
        } catch (Exception e) {
            //todo capturar erorres de db
            throw new DataEntityException(e.getMessage());
        }
    }
}