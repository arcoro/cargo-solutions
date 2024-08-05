package com.cargo.solutions.domain.ports.in;

import com.cargo.solutions.domain.model.Page;

import java.util.List;
import java.util.Optional;

public interface PageService {
    Optional<Page> getPage(Integer pageId);

    List<Page> listAllPages();

    Page createPage(Page page);

    Page updatePage(Integer pageId, Page newPage);

    void deletePage(Integer pageId);
}
