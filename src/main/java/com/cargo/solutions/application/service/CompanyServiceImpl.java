package com.cargo.solutions.application.service;

import com.cargo.solutions.domain.exception.DataEntityException;
import com.cargo.solutions.domain.exception.NotFoundEntityException;
import com.cargo.solutions.domain.model.Company;
import com.cargo.solutions.domain.ports.in.CompanyService;
import com.cargo.solutions.domain.ports.out.CompanyRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Optional<Company> getCompany(UUID companyId) {
        return companyRepository.findById(companyId);
    }

    @Override
    public List<Company> listAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company createCompany(Company company) {
        return save(Company.builder().companyName(company.getCompanyName())
                .identifier(company.getIdentifier())
                .phone(company.getPhone())
                .email(company.getEmail())
                .highStreet(company.getHighStreet())
                .sideStreet(company.getSideStreet())
                .identityTypeId(company.getIdentityTypeId())
                .identity(company.getIdentity())
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Override
    public Company updateCompany(UUID companyId, Company newCompany) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new NotFoundEntityException("Not found Entity"));
        company.setCompanyName(newCompany.getCompanyName());
        company.setIdentifier(newCompany.getIdentifier());
        company.setPhone(newCompany.getPhone());
        company.setEmail(newCompany.getEmail());
        company.setHighStreet(newCompany.getHighStreet());
        company.setSideStreet(newCompany.getSideStreet());
        company.setIdentityTypeId(newCompany.getIdentityTypeId());
        company.setIdentity(newCompany.getIdentity());
        company.setUpdatedAt(LocalDateTime.now());
        return save(company);
    }

    @Override
    public void deleteCompany(UUID companyId) {
        companyRepository.deleteById(companyId);
    }

    private Company save(Company company) {
        try {
            return companyRepository.save(company);
        } catch (Exception e) {
            //todo capturar erorres de db
            throw new DataEntityException(e.getMessage());
        }
    }
}