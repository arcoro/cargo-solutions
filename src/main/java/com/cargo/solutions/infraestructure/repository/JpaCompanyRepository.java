package com.cargo.solutions.infraestructure.repository;

import com.cargo.solutions.infraestructure.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaCompanyRepository extends JpaRepository<CompanyEntity, UUID> {

}
