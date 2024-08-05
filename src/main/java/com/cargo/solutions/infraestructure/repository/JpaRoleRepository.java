package com.cargo.solutions.infraestructure.repository;

import com.cargo.solutions.infraestructure.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface JpaRoleRepository extends JpaRepository<RoleEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE roles SET role = :role, updated_at = :updatedAt WHERE id = :roleId", nativeQuery = true)
    int updateRole(@Param("roleId") Integer roleId,
                   @Param("role") String role,
                   @Param("updatedAt") LocalDateTime updatedAt);

    @Query(value = "select role from roles r left join users u on r.id = u.role_id where u.email = :mail", nativeQuery = true)
    String getRoleByUser(String mail);

}

