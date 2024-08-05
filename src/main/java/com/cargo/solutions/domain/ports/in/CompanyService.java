package com.cargo.solutions.domain.ports.in;

import com.cargo.solutions.domain.model.Company;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyService {
    Optional<Company> getCompany(UUID companyId);

    List<Company> listAllCompanies();

    Company createCompany(Company company);

    Company updateCompany(UUID companyId, Company newCompany);

    void deleteCompany(UUID companyId);
}
