package com.cargo.solutions.domain.ports.out;

import com.cargo.solutions.domain.model.Company;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Optional<Company> findById(UUID companyId);

    Company save(Company company);

    void deleteById(UUID companyId);

    List<Company> findAll();
}
