package com.cargo.solutions.infraestructure.repository.imp;

import com.cargo.solutions.domain.model.Company;
import com.cargo.solutions.domain.ports.out.CompanyRepository;
import com.cargo.solutions.infraestructure.entity.CompanyEntity;
import com.cargo.solutions.infraestructure.repository.JpaCompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {

    private final JpaCompanyRepository jpaCompanyRepository;

    @Override
    public Optional<Company> findById(UUID companyId) {
        return jpaCompanyRepository.findById(companyId).map(this::toDomain);
    }

    @Override
    public Company save(Company company) {
        CompanyEntity entity = toEntity(company);
        CompanyEntity savedEntity = jpaCompanyRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID companyId) {
        jpaCompanyRepository.deleteById(companyId);
    }

    @Override
    public List<Company> findAll() {
       return jpaCompanyRepository.findAll().stream().map(this::toDomain).toList();
    }

    private CompanyEntity toEntity(Company company) {
        CompanyEntity entity = new CompanyEntity();
        entity.setId(company.getId());
        entity.setCompanyName(company.getCompanyName());
        entity.setIdentifier(company.getIdentifier());
        entity.setPhone(company.getPhone());
        entity.setEmail(company.getEmail());
        entity.setHighStreet(company.getHighStreet());
        entity.setSideStreet(company.getSideStreet());
        entity.setIdentityTypeId(company.getIdentityTypeId());
        entity.setIdentity(company.getIdentity());
        entity.setCreatedAt(company.getCreatedAt());
        entity.setUpdatedAt(company.getUpdatedAt());
        return entity;
    }

    private Company toDomain(CompanyEntity entity) {
        Company company = new Company();
        company.setId(entity.getId());
        company.setCompanyName(entity.getCompanyName());
        company.setIdentifier(entity.getIdentifier());
        company.setPhone(entity.getPhone());
        company.setEmail(entity.getEmail());
        company.setHighStreet(entity.getHighStreet());
        company.setSideStreet(entity.getSideStreet());
        company.setIdentityTypeId(entity.getIdentityTypeId());
        company.setIdentity(entity.getIdentity());
        company.setCreatedAt(entity.getCreatedAt());
        company.setUpdatedAt(entity.getUpdatedAt());
        return company;
    }
}
