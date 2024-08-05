package com.cargo.solutions.infraestructure.repository.imp;

import com.cargo.solutions.domain.dto.PageAction;
import com.cargo.solutions.domain.dto.PageRoleAction;
import com.cargo.solutions.domain.model.Page;
import com.cargo.solutions.domain.model.PageRole;
import com.cargo.solutions.domain.ports.out.PageRoleRepository;
import com.cargo.solutions.infraestructure.entity.PageEntity;
import com.cargo.solutions.infraestructure.entity.PageRoleEntity;
import com.cargo.solutions.infraestructure.repository.JpaPageRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class PageRoleRepositoryImpl implements PageRoleRepository {

    private final JpaPageRoleRepository jpaPageRoleRepository;

    @Override
    public Optional<PageRole> findById(Integer pageRoleId) {
        return jpaPageRoleRepository.findById(pageRoleId).map(this::toDomain);
    }

    @Override
    public PageRole save(PageRole pageRole) {
        PageRoleEntity entity = toEntity(pageRole);
        PageRoleEntity savedEntity = jpaPageRoleRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public int update(PageRole pageRole) {
        return jpaPageRoleRepository.updatePageRole(pageRole.getId(), pageRole.getPageId(), pageRole.getRoleId(), pageRole.getActions());
    }

    @Override
    public void deleteById(Integer pageRoleId) {
        jpaPageRoleRepository.deleteById(pageRoleId);
    }

    @Override
    public List<PageRole> findAll() {
        return jpaPageRoleRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public PageRoleAction findByRole(String role) {
        List<PageEntity> pageList = jpaPageRoleRepository.findByRole(role);
        PageRoleAction pageRoleAction = PageRoleAction.builder().actions(new ArrayList<>()).build();
        for (PageEntity pageEntity : pageList) {
            pageRoleAction.getActions().add(toPageRoleAction(pageEntity));
        }
        return pageRoleAction;
    }

    private PageRoleEntity toEntity(PageRole pageRole) {
        PageRoleEntity entity = new PageRoleEntity();
        entity.setId(pageRole.getId());
        entity.setPageId(pageRole.getPageId());
        entity.setRoleId(pageRole.getRoleId());
        entity.setActions(pageRole.getActions());
        return entity;
    }

    private PageRole toDomain(PageRoleEntity entity) {
        PageRole pageRole = new PageRole();
        pageRole.setId(entity.getId());
        pageRole.setPageId(entity.getPageId());
        pageRole.setRoleId(entity.getRoleId());
        pageRole.setActions(entity.getActions());
        return pageRole;
    }

    private PageAction toPageRoleAction(PageEntity pageEntity) {
        Page page = Page.builder()
                .id(pageEntity.getId())
                .pageType(pageEntity.getPageType())
                .icon(pageEntity.getIcon())
                .path(pageEntity.getPath())
                .title(pageEntity.getTitle())
                .previousMenu(pageEntity.getPreviousMenu())
                .build();
        return PageAction.builder()
                .page(page)
                .actions(pageEntity.getActions())
                .build();
    }

}
