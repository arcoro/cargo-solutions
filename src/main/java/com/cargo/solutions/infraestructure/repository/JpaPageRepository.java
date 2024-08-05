package com.cargo.solutions.infraestructure.repository;

import com.cargo.solutions.infraestructure.entity.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JpaPageRepository extends JpaRepository<PageEntity, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE PageEntity p SET " +
            "p.pageType = :pageType, " +
            "p.icon = :icon, " +
            "p.path = :path, " +
            "p.title = :title, " +
            "p.actions = :actions, " +
            "p.previousMenu = :previousMenu " +
            "WHERE p.id = :pageId")
    int updatePage(@Param("pageId") Integer pageId,
                   @Param("pageType") String pageType,
                   @Param("icon") String icon,
                   @Param("path") String path,
                   @Param("title") String title,
                   @Param("actions") List<String> actions,
                   @Param("previousMenu") Integer previousMenu);
}
