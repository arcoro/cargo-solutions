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
import com.cargo.solutions.domain.model.Role;
import com.cargo.solutions.domain.ports.in.RoleService;
import com.cargo.solutions.infraestructure.vo.RoleVo;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.listAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRole(@PathVariable(name = "roleId")  Integer roleId) {
        return ResponseEntity.ok(roleService.getRole(roleId).orElseThrow(() -> new NotFoundEntityException("Not found Entity")));
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@Valid @RequestBody RoleVo roleVo) {
        Role createdRole = roleService.createRole(objectMapper.convertValue(roleVo, Role.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable(name = "roleId") Integer roleId, @Valid @RequestBody RoleVo roleVo) {
        Role updatedRole = roleService.updateRole(roleId, objectMapper.convertValue(roleVo, Role.class));
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable(name = "roleId") Integer roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.ok().build();
    }

}
