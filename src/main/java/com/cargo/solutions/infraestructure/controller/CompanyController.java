package com.cargo.solutions.infraestructure.controller;

import com.cargo.solutions.domain.exception.NotFoundEntityException;
import com.cargo.solutions.domain.model.Company;
import com.cargo.solutions.domain.ports.in.CompanyService;
import com.cargo.solutions.infraestructure.vo.CompanyVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.listAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompany(@PathVariable UUID companyId) {
        return ResponseEntity.ok(companyService.getCompany(companyId).orElseThrow(() -> new NotFoundEntityException("Not found Entity")));
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@Valid @RequestBody CompanyVo companyVo) {
        Company createdCompany = companyService.createCompany(objectMapper.convertValue(companyVo, Company.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<Company> updateCompany(@PathVariable UUID companyId, @Valid @RequestBody CompanyVo companyVo) {
        Company updatedCompany = companyService.updateCompany(companyId, objectMapper.convertValue(companyVo, Company.class));
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable UUID companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.ok().build();
    }

}
