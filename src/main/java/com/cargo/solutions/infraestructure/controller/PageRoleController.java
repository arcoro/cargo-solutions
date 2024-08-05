package com.cargo.solutions.infraestructure.controller;

import com.cargo.solutions.domain.dto.PageRoleAction;
import com.cargo.solutions.domain.exception.NotFoundEntityException;
import com.cargo.solutions.domain.model.PageRole;
import com.cargo.solutions.domain.ports.in.PageRoleService;
import com.cargo.solutions.infraestructure.vo.PageRoleVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pages-role")
public class PageRoleController {

    private final PageRoleService pageRoleService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<PageRole>> getAllPageRoles() {
        List<PageRole> pageRoles = pageRoleService.listAllPageRoles();
        return ResponseEntity.ok(pageRoles);
    }

    @GetMapping("/{pageRoleId}")
    public ResponseEntity<PageRole> getPageRole(@PathVariable(name = "pageRoleId") Integer pageRoleId) {
        return ResponseEntity.ok(pageRoleService.getPageRole(pageRoleId)
                .orElseThrow(() -> new NotFoundEntityException("PageRole not found")));
    }

    @PostMapping
    public ResponseEntity<PageRole> createPageRole(@Valid @RequestBody PageRoleVo pageRoleVo) {
        PageRole createdPageRole = pageRoleService.createPageRole(objectMapper.convertValue(pageRoleVo, PageRole.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPageRole);
    }

    @PutMapping("/{pageRoleId}")
    public ResponseEntity<PageRole> updatePageRole(@PathVariable(name = "pageRoleId") Integer pageRoleId, @Valid @RequestBody PageRoleVo pageRoleVo) {
        PageRole updatedPageRole = pageRoleService.updatePageRole(pageRoleId, objectMapper.convertValue(pageRoleVo, PageRole.class));
        return ResponseEntity.ok(updatedPageRole);
    }

    @DeleteMapping("/{pageRoleId}")
    public ResponseEntity<Void> deletePageRole(@PathVariable(name = "pageRoleId") Integer pageRoleId) {
        pageRoleService.deletePageRole(pageRoleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/actions")
    public ResponseEntity<PageRoleAction> listActions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GrantedAuthority role = authentication.getAuthorities().stream().findFirst().orElse(null);
        return ResponseEntity.ok().body(pageRoleService.getPageRoleAction(String.valueOf(role)));
    }
}
