package com.cargo.solutions.infraestructure.repository;

import com.cargo.solutions.infraestructure.entity.SecurityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaSecurityRepository extends JpaRepository<SecurityEntity, UUID> {

    Optional<SecurityEntity> findByUserId(UUID userId);

}
