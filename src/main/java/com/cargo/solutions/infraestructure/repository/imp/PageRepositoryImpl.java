package com.cargo.solutions.infraestructure.repository.imp;

import com.cargo.solutions.domain.model.Page;
import com.cargo.solutions.domain.ports.out.PageRepository;
import com.cargo.solutions.infraestructure.entity.PageEntity;
import com.cargo.solutions.infraestructure.repository.JpaPageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class PageRepositoryImpl implements PageRepository {

    private final JpaPageRepository jpaPageRepository;

    @Override
    public Optional<Page> findById(Integer pageId) {
        return jpaPageRepository.findById(pageId).map(this::toDomain);
    }

    @Override
    public Page save(Page page) {
        PageEntity entity = toEntity(page);
        PageEntity savedEntity = jpaPageRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public int update(Page page) {
        return jpaPageRepository.updatePage(page.getId(), page.getPageType(), page.getIcon(),
                page.getPath(), page.getTitle(), page.getActions(), page.getPreviousMenu());
    }

    @Override
    public void deleteById(Integer pageId) {
        jpaPageRepository.deleteById(pageId);
    }

    @Override
    public List<Page> findAll() {
        return jpaPageRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    private PageEntity toEntity(Page page) {
        PageEntity entity = new PageEntity();
        entity.setId(page.getId());
        entity.setPageType(page.getPageType());
        entity.setIcon(page.getIcon());
        entity.setPath(page.getPath());
        entity.setTitle(page.getTitle());
        entity.setActions(page.getActions());
        entity.setPreviousMenu(page.getPreviousMenu());
        return entity;
    }

    private Page toDomain(PageEntity entity) {
        Page page = new Page();
        page.setId(entity.getId());
        page.setPageType(entity.getPageType());
        page.setIcon(entity.getIcon());
        page.setPath(entity.getPath());
        page.setTitle(entity.getTitle());
        page.setActions(entity.getActions());
        page.setPreviousMenu(entity.getPreviousMenu());
        return page;
    }
}
