package com.cargo.solutions.infraestructure.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cargo.solutions.domain.exception.NotFoundEntityException;
import com.cargo.solutions.domain.model.Page;
import com.cargo.solutions.domain.ports.in.PageService;
import com.cargo.solutions.infraestructure.vo.PageVo;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pages")
public class PageController {

    private final PageService pageService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Page>> getAllPages() {
        List<Page> pages = pageService.listAllPages();
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/{pageId}")
    public ResponseEntity<Page> getPage(@PathVariable(name = "pageId") Integer pageId) {
        return ResponseEntity.ok(pageService.getPage(pageId)
                .orElseThrow(() -> new NotFoundEntityException("Page not found")));
    }

    @PostMapping
    public ResponseEntity<Page> createPage(@Valid @RequestBody PageVo pageVo) {
        Page createdPage = pageService.createPage(objectMapper.convertValue(pageVo, Page.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPage);
    }

    @PutMapping("/{pageId}")
    public ResponseEntity<Page> updatePage(@PathVariable(name = "pageId") Integer pageId, @Valid @RequestBody PageVo pageVo) {
        Page updatedPage = pageService.updatePage(pageId, objectMapper.convertValue(pageVo, Page.class));
        return ResponseEntity.ok(updatedPage);
    }

    @DeleteMapping("/{pageId}")
    public ResponseEntity<Void> deletePage(@PathVariable(name = "pageId") Integer pageId) {
        pageService.deletePage(pageId);
        return ResponseEntity.ok().build();
    }
}
