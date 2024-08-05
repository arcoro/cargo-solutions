package com.cargo.solutions.infraestructure.repository.imp;

import com.cargo.solutions.domain.model.Role;
import com.cargo.solutions.domain.ports.out.RoleRepository;
import com.cargo.solutions.infraestructure.entity.RoleEntity;
import com.cargo.solutions.infraestructure.repository.JpaRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final JpaRoleRepository jpaRoleRepository;

    @Override
    public Optional<Role> findById(Integer roleId) {
        return jpaRoleRepository.findById(roleId).map(this::toDomain);
    }

    @Override
    public Role save(Role role) {
        RoleEntity entity = toEntity(role);
        RoleEntity savedEntity = jpaRoleRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public int update(Role role) {
        return jpaRoleRepository.updateRole(role.getId(), role.getRole(), role.getUpdatedAt());
    }

    @Override
    public void deleteById(Integer roleId) {
        jpaRoleRepository.deleteById(roleId);
    }

    @Override
    public List<Role> findAll() {
        return jpaRoleRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public String getRoleByUser(String username) {
        return jpaRoleRepository.getRoleByUser(username);
    }

    private RoleEntity toEntity(Role role) {
        RoleEntity entity = new RoleEntity();
        entity.setId(role.getId());
        entity.setRole(role.getRole());
        entity.setCreatedAt(role.getCreatedAt());
        entity.setUpdatedAt(role.getUpdatedAt());
        return entity;
    }

    private Role toDomain(RoleEntity entity) {
        Role role = new Role();
        role.setId(entity.getId());
        role.setRole(entity.getRole());
        role.setCreatedAt(entity.getCreatedAt());
        role.setUpdatedAt(entity.getUpdatedAt());
        return role;
    }
}
