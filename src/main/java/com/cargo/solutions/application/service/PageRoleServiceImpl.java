package com.cargo.solutions.application.service;

import com.cargo.solutions.domain.dto.PageRoleAction;
import com.cargo.solutions.domain.exception.DataEntityException;
import com.cargo.solutions.domain.model.Page;
import com.cargo.solutions.domain.model.PageRole;
import com.cargo.solutions.domain.ports.in.CacheService;
import com.cargo.solutions.domain.ports.in.PageRoleService;
import com.cargo.solutions.domain.ports.in.PageService;
import com.cargo.solutions.domain.ports.out.PageRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.cargo.solutions.domain.ctes.KeyConstants.ROLE_PAGE_CACHE_KEY;

@AllArgsConstructor
public class PageRoleServiceImpl implements PageRoleService {

    private final PageRoleRepository pageRoleRepository;

    private final PageService pageService;

    private final CacheService cacheService;

    private final ObjectMapper objectMapper;

    @Override
    public Optional<PageRole> getPageRole(Integer pageRoleId) {
        return pageRoleRepository.findById(pageRoleId);
    }

    @Override
    public List<PageRole> listAllPageRoles() {
        return pageRoleRepository.findAll();
    }

    @Override
    public PageRole createPageRole(PageRole pageRole) {
        Optional<Page> optionalPage = pageService.getPage(pageRole.getPageId());
        if (optionalPage.isEmpty()) throw new DataEntityException("Page not found");
        validateActions(pageRole);
        return save(pageRole);
    }

    @Override
    public PageRole updatePageRole(Integer pageRoleId, PageRole newPageRole) {
        newPageRole.setId(pageRoleId);
        validateActions(newPageRole);
        try {
            if (pageRoleRepository.update(newPageRole) == 0) {
                throw new DataEntityException("PageRole not found");
            }
        } catch (Exception e) {
            throw new DataEntityException(e.getMessage());
        }
        return newPageRole;
    }

    @Override
    public void deletePageRole(Integer pageRoleId) {
        pageRoleRepository.deleteById(pageRoleId);
    }

    private PageRole save(PageRole pageRole) {
        try {
            return pageRoleRepository.save(pageRole);
        } catch (Exception e) {
            throw new DataEntityException(e.getMessage());
        }
    }

    private void validateActions(PageRole pageRole) {
        Optional<Page> optionalPage = pageService.getPage(pageRole.getPageId());
        if (optionalPage.isEmpty()) throw new DataEntityException("Page not found");
        List<String> actions = pageRole.getActions();
        List<String> actionsPage = optionalPage.get().getActions();
        if (!actions.isEmpty()) {
            if (actionsPage.isEmpty()) {
                throw new DataEntityException("invalid actions");
            }

            actions.forEach(action -> {
                if (!actionsPage.contains(action)) {
                    throw new DataEntityException("Invalid action: '" + action + "'");
                }
            });

        }
    }

    @Override
    public PageRoleAction getPageRoleAction(String role) {
        Optional<Object> optional = cacheService.get(String.format(ROLE_PAGE_CACHE_KEY, role));
        if (optional.isPresent()) return objectMapper.convertValue(optional.get(), PageRoleAction.class);
        PageRoleAction pageRoleAction = pageRoleRepository.findByRole(role);
        cacheService.set(String.format(ROLE_PAGE_CACHE_KEY, role), pageRoleAction);
        return pageRoleAction;

    }

}
