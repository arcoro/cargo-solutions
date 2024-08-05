package com.cargo.solutions.infraestructure.repository;

import com.cargo.solutions.infraestructure.entity.PageEntity;
import com.cargo.solutions.infraestructure.entity.PageRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JpaPageRoleRepository extends JpaRepository<PageRoleEntity, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE PageRoleEntity p SET " +
            "p.pageId = :pageId, " +
            "p.roleId = :roleId, " +
            "p.actions = :actions " +
            "WHERE p.id = :pageRoleId")
    int updatePageRole(@Param("pageRoleId") Integer pageRoleId,
                       @Param("pageId") Integer pageId,
                       @Param("roleId") Integer roleId,
                       @Param("actions") List<String> actions);

    @Query("SELECT new PageEntity(p.id, " +
            "p.pageType, " +
            "p.icon, " +
            "p.path, " +
            "p.title, " +
            "pr.actions, " +
            "p.previousMenu) " +
            "FROM PageEntity p " +
            "LEFT JOIN PageRoleEntity pr ON p.id = pr.pageId " +
            "LEFT JOIN RoleEntity r ON pr.roleId = r.id " +
            "WHERE r.role = :role")
    List<PageEntity> findByRole(String role);


}
