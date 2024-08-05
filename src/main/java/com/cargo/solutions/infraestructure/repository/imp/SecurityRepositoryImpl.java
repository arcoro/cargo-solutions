package com.cargo.solutions.infraestructure.repository.imp;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.cargo.solutions.domain.model.Security;
import com.cargo.solutions.domain.ports.out.SecurityRepository;
import com.cargo.solutions.infraestructure.entity.SecurityEntity;
import com.cargo.solutions.infraestructure.repository.JpaSecurityRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class SecurityRepositoryImpl implements SecurityRepository {

    private final JpaSecurityRepository jpaSecurityRepository;

    @Override
    public Optional<Security> findByUserId(UUID userId) {
        return jpaSecurityRepository.findByUserId(userId).map(this::toDomain);
    }

    @Override
    public Security save(Security security) {
        SecurityEntity entity = new SecurityEntity();
        entity.setId(security.getId());
        entity.setUserId(security.getUserId());
        entity.setPassword(security.getPassword());
        entity.setFirstLogin(security.getFirstLogin());
        entity.setLastLogin(security.getLastLogin());
        entity.setIntents(security.getIntents());
        entity.setHistoryPasswords(security.getHistoryPasswords());
        entity.setValidated(security.isValidated());
        entity.setEnabled(security.isEnabled());
        entity.setBlocked(security.isBlocked());
        entity.setPasswordExpiresAt(security.getPasswordExpiresAt());
        entity.setVerificationCode(security.getVerificationCode());
        entity.setRecoveryCode(security.getRecoveryCode());
        entity.setOtpCode(security.getOtpCode());
        SecurityEntity savedEntity = jpaSecurityRepository.save(entity);
        return toDomain(savedEntity);
    }

    private Security toDomain(SecurityEntity entity) {
        Security security = new Security();
        security.setId(entity.getId());
        security.setUserId(entity.getUserId());
        security.setPassword(entity.getPassword());
        security.setFirstLogin(entity.getFirstLogin());
        security.setLastLogin(entity.getLastLogin());
        security.setIntents(entity.getIntents());
        security.setHistoryPasswords(entity.getHistoryPasswords());
        security.setValidated(entity.isValidated());
        security.setEnabled(entity.isEnabled());
        security.setBlocked(entity.isBlocked());
        security.setPasswordExpiresAt(entity.getPasswordExpiresAt());
        security.setVerificationCode(entity.getVerificationCode());
        security.setRecoveryCode(entity.getRecoveryCode());
        security.setOtpCode(entity.getOtpCode());
        return security;
    }

}
