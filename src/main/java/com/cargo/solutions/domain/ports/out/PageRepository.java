package com.cargo.solutions.domain.ports.out;

import com.cargo.solutions.domain.model.Page;

import java.util.List;
import java.util.Optional;

public interface PageRepository {
    Optional<Page> findById(Integer pageId);

    Page save(Page page);

    int update(Page page);

    void deleteById(Integer pageId);

    List<Page> findAll();
}
