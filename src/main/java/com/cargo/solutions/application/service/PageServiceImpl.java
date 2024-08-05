package com.cargo.solutions.application.service;

import com.cargo.solutions.domain.exception.DataEntityException;
import com.cargo.solutions.domain.model.Page;
import com.cargo.solutions.domain.ports.in.PageService;
import com.cargo.solutions.domain.ports.out.PageRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    @Override
    public Optional<Page> getPage(Integer pageId) {
        return pageRepository.findById(pageId);
    }

    @Override
    public List<Page> listAllPages() {
        return pageRepository.findAll();
    }

    @Override
    public Page createPage(Page page) {
        return save(page);
    }

    @Override
    public Page updatePage(Integer pageId, Page newPage) {
        newPage.setId(pageId);
        if (pageRepository.update(newPage) == 0) {
            throw new DataEntityException("Page not found");
        }
        return newPage;
    }

    @Override
    public void deletePage(Integer pageId) {
        pageRepository.deleteById(pageId);
    }

    private Page save(Page page) {
        try {
            return pageRepository.save(page);
        } catch (Exception e) {
            throw new DataEntityException(e.getMessage());
        }
    }
}
